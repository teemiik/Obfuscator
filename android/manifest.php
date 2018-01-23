
<?php

    //Устанавливаем кодировку и вывод всех ошибок
	header('Content-Type: text/html; charset=UTF-8');

    if (empty($_POST['path'])) {
        echo 'Не указана папка с приложением';
        exit;
    }

    if(isset($_POST['upload']))
	{   

        ////////////////////-----------------добавляем разрешений---------------------//////////////////
        $dirManifest = 'Projects/'.$_POST['path'].'/app/src/main/AndroidManifest.xml';
        $strfile = file_get_contents($dirManifest);

        if (!empty($_POST['uses'])) {

            $strGradle = file_get_contents($dirManifest);
            $strGradle = str_replace('//PMU', $_POST['uses'], $strGradle);
            file_put_contents($dirManifest, $strGradle);
        }

        ////////////////////-----------------изменяем расположение активити---------------------//////////////////
        $xml = new DOMDocument; 
        $xml->load($dirManifest);
        $manifest = $xml->documentElement;
        $application = $manifest->getElementsByTagName('application')->item(0);
        $activities = $manifest->getElementsByTagName('activity');
        $arrayActivities = array();

        foreach ($activities as $activity) {
            array_push($arrayActivities, $activity);
        }

        foreach ($arrayActivities as $activity) {
            $application->removeChild($activity);
        }

        $countActivities = count($arrayActivities);
        $elem = generateFunction($countActivities, $countActivities - 1);

        foreach ($elem as $value) {
            $application->appendChild($arrayActivities[$value]);
        }

        $xml->save($dirManifest);

        ////////////////////-----------------изменяем расстояние между строками---------------------//////////////////
        $randEnter = rand(0, 6);
        $strfile = file_get_contents($dirManifest);
        for ($i = 0; $i <= $randEnter; $i++) {
            $str_replace = str_replace("\n", "\n\n", $strfile);
        }
        file_put_contents($dirManifest, $str_replace);
        
        if (!empty($_POST['checkCompile'])) {
            ////////////////////-----------------формируем батник---------------------//////////////////
            $dirbat = 'build.bat';
            $strBuild = "cd Projects/".$_POST['path']."\r\ngradlew assembleRelease";
            file_put_contents($dirbat, $strBuild);

            exec('build.bat');
            system('build.bat');
        }
    }


    //////----------функции, используемые в проекте--------------------------////////////
    function generateFunction($limit, $max_num) {
        $used_nums = array(); 
        while(1) { 
          $random = rand(0, $max_num); 
          if(!in_array($random, $used_nums)) { 
             $used_nums[] = $random; 
          } 
          if(count($used_nums) == $limit) { break; } 
        } 
        return $used_nums; 
    }