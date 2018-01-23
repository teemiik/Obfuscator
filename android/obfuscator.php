
<?php

    //Устанавливаем кодировку и вывод всех ошибок
	header('Content-Type: text/html; charset=UTF-8');

    if (empty($_POST['path'])) {
        echo 'Не указана папка с приложением';
        exit;
    }

    if(isset($_POST['upload']))
	{   
        /*$strfile = file_get_contents('functions.xml');
        for ($i = 1; $i <= 10; $i++) {
            $strfile = str_replace("arrowLeft", "strelkaVlevo", $strfile);
            $strfile = str_replace("arrowRight", "strelkaVparavo", $strfile);
            $strfile = str_replace("\"", "kavichka", $strfile);
            $strfile = str_replace("'", "kavichkaOdna", $strfile);
        }
        file_put_contents('functions.xml', $strfile);*/

        $dirString = search_file('Projects/'.$_POST['path'].'/app/src/main/java/', 'MainActivity.java', true);

        $allclass = scandir($dirString);

        ///////////------------------смена имен переменных и функций во всех файлах------------------------------------//////////////
        if (!empty($_POST['checkParam'])) {
            (!empty($_POST['countChar'])) ? $countChar = $_POST['countChar'] : $countChar = 8;
            foreach ($allclass as $value) {
                if(!is_dir($value)) {
                        if ($value != '.' || $value != '..' || $value != '.DS_Store') {
                            $strfile = file_get_contents($dirString."/".$value);
                        if(preg_match_all('(#(.*)#)', $strfile, $matches)){
                            $i = 1;
                            $x = 0;
                            $stack = array();
                            foreach($matches as $match) {
                                foreach($match as $val_match) {       
                                    if ($i % 2 != 0) {
                                        $name_rand = generateWord($countChar);
                                        $strfile = str_replace($val_match, $name_rand, $strfile);
                                        array_push($stack, $name_rand);
                                    } else {
                                        if (!empty($stack)) {
                                            $strfile = str_replace($val_match, $stack[$x++], $strfile);
                                            $count = count($stack);
                                            if ($x == $count) {
                                                $stack = array();
                                                $x = 0;
                                            }

                                            file_put_contents($dirString."/".$value, $strfile);
                                        }                                        
                                    }                               
                                }           
                                $i++;
                            }
                        } 
                    }
                }
            }
        }

        ///////////--------------------------------добавление пустых функций----------------------------------------///////////////////
       if (!empty($_POST['func'])) {
            $dirFunctionsXml = 'functions.xml';
            if (file_exists($dirFunctionsXml)) {
                foreach($allclass as $val_cla) {
                    if(!is_dir($val_cla)) {
                        if ($val_cla != '.' || $val_cla != '..' || $val_cla != '.DS_Store') {

                            $strfile = file_get_contents($dirString."/".$val_cla);

                            if (preg_match('/Activity/', $strfile) || preg_match('/AppCompatActivity/', $strfile)) {
                                $dirClass = search_file('Projects/'.$_POST['path'].'/app/src/main/java/', $val_cla);

                                for($i = 1; $i <= $_POST['func']; $i++) {
                                    $randomFunction = generateFunction($_POST['func'], 10);
                                    $xml = simplexml_load_file($dirFunctionsXml);
                                    
                                    $strfile = str_replace('//initfunc'.$i, (string)$xml->{'function'.$i}->name, $strfile);
                                    $strfile = str_replace('//funct'.$i, (string)$xml->{'function'.$i}->code, $strfile);
                                    $strfile = str_replace('//pactf'.$i, (string)$xml->{'function'.$i}->import, $strfile);

                                    $strfile = str_replace("strelkaVlevo", "<", $strfile);
                                    $strfile = str_replace("strelkaVparavo", ">", $strfile);
                                    $strfile = str_replace("kavichka", "\"", $strfile);
                                    $strfile = str_replace("kavichkaOdna", "'", $strfile);
                                }
                                file_put_contents($dirClass, $strfile);
                            }
                        }
                    }
                }
            } else {
                exit('Не удалось открыть файл functions.xml в корне');
                exit;
            }
        }
        
        /////////////--------------------------------добавление библиотек-------------------------------------///////////////////
        if (!empty($_POST['library'])) {
            $dirGradle = 'Projects/'.$_POST['path'].'/app/build.gradle';
            
            $strGradle = file_get_contents($dirGradle);
            $strGradle = str_replace('//addlib', $_POST['library'], $strGradle);
            file_put_contents($dirGradle, $strGradle);
        }

        //////////////------------------------------изменение структуры проекта--------------------------------////////////////////
        if (!empty($_POST['struct'])) {

            $pathStruct = $dirString;
            for ($i = 0; $i < $_POST['struct']; $i++) {
                $randStruct = generateWord();
                $pathStruct = $pathStruct."/".$randStruct;
            }

            mkdir($pathStruct, 0777, true);

            foreach ($allclass as $value) {
                if(!is_dir($value)) {
                    rename($dirString.'/'.$value, $pathStruct.'/'.$value);
                }
            
            }

            $oldPositionStr = stripos($dirString, 'java/');
            $oldPackageDemo = substr($dirString, $oldPositionStr + 6);
            $oldPathPackage = str_replace('/', '.', $oldPackageDemo);

            $newPositionStr = stripos($pathStruct, 'java/');
            $newPackageDemo = substr($pathStruct, $newPositionStr + 6);
            $newPathPackage = str_replace('/', '.', $newPackageDemo);

            //смена имени пакета в классах
            $allclass = scandir($pathStruct);

            foreach ($allclass as $value) {
                if(!is_dir($value)) {
                    $strfile = file_get_contents($pathStruct."/".$value);
                    $str_replace = str_replace($oldPathPackage, $newPathPackage, $strfile);
                    file_put_contents($pathStruct."/".$value, $str_replace);
                }
            }

            //смена имени пакета в манифесте
            $dirmanifest = 'Projects/'.$_POST['path'].'/app/src/main/AndroidManifest.xml';
            $strfile = file_get_contents($dirmanifest);
            $str_replace = str_replace($oldPathPackage, $newPathPackage, $strfile);
            file_put_contents($dirmanifest, $str_replace);

            //смена имени пакета в файле gradle
            $dirgradle = 'Projects/'.$_POST['path'].'/app/build.gradle';
            $strfile = file_get_contents($dirgradle);
            $str_replace = str_replace($oldPathPackage, $newPathPackage, $strfile);
            file_put_contents($dirgradle, $str_replace);
            
        }

        //////////////------------------------------------изменение имен активити---------------------------------////////////////////
        if (!empty($_POST['checkClass'])) {

            $dirString = search_file('Projects/'.$_POST['path'].'/app/src/main/java/', 'MainActivity.java', true);

            $allclass = scandir($dirString);

            $dirManifest = 'Projects/'.$_POST['path'].'/app/src/main/AndroidManifest.xml';

            foreach ($allclass as $value) {
                if(!is_dir($value)) {
                    if ($value != '.' || $value != '..' || $value != '.DS_Store') {

                        $randNameClass = generateWord();

                        $strfile = file_get_contents($dirString."/".$value);
                        
                        if (preg_match('/Activity/', $strfile) || preg_match('/AppCompatActivity/', $strfile)) 
                        {   
                            $splitfile = split('\.', $value);

                            $strfile = str_replace($splitfile[0], $randNameClass, $strfile);
                            file_put_contents($dirString."/".$value, $strfile);
                            
                            //проверяем присутствие активти в других активти и заменяем название
                            $newAllClass = scandir($dirString);
                            foreach ($newAllClass as $vc) {
                                if(!is_dir($vc)) {
                                    if ($vc != '.' || $vc != '..' || $vc != '.DS_Store') {

                                        $strfile = file_get_contents($dirString."/".$vc);

                                        if (preg_match('/Activity/', $strfile) || preg_match('/AppCompatActivity/', $strfile)) {
                                            $strfile = file_get_contents($dirString."/".$vc);
                                            $strfile = str_replace($splitfile[0], $randNameClass, $strfile);
                                            file_put_contents($dirString."/".$vc, $strfile);
                                        }
                                    }
                                }
                            }

                            rename($dirString.'/'.$value, $dirString.'/'.$randNameClass.'.java');

                            $strfile = file_get_contents($dirManifest);
                            $strfile = str_replace($splitfile[0], $randNameClass, $strfile);
                            file_put_contents($dirManifest, $strfile);
                        }
                    }
                }
            }
        }

        //////////////---------------------------------замена ссылки-------------------------------------////////////////////
        if (!empty($_POST['url_site1'])) {

            (!empty($_POST['url_site2'])) ? $urlWeb = $_POST['url_site2'] : $urlWeb = "";

            $dirLink = 'Projects/'.$_POST['path'].'/app/src/main/res/values/strings.xml'; 
            if (file_exists($dirLink)) {
                $xml = simplexml_load_file($dirLink);

                if (isset($xml->string[2])) { 

                    //смена ссылки на сайт 1
                    $xml->string[1] = $_POST['url_site1'];

                    //смена ссылки на сайт 2
                    if ($urlWeb != "") {
                        $xml->string[2] = $urlWeb;
                    }
                
                    $xml->asXML($dirLink);
               } else if (isset($xml->string[1])) {

                    //смена ссылки на сайт 1
                    $xml->string[1] = $_POST['url_site1'];

                    $xml->asXML($dirLink);
               } else {
                    exit('Неправильно составлен файл strings.xml');
                    exit;
               }
            } else {
                exit('Не удалось открыть файл string.xml в проекте');
                exit;
            }
        }

        //////////////-------------------------------вставка ключа----------------------------------/////////////////
        if(!empty($_FILES))
		{
            $type = pathinfo($_FILES['keyFiles']['name']);
            $name = 'Projects/'.$_POST['path'].'/app/'.$type['basename'];
            move_uploaded_file($_FILES['keyFiles']['tmp_name'],$name);

            $type = pathinfo($_FILES['keyXml']['name']);
            $name = 'Projects/'.$_POST['path'].'/app/'.$type['basename'];
            move_uploaded_file($_FILES['keyXml']['tmp_name'],$name);

            $xml = simplexml_load_file($name);

            $dataKey = 'storeFile file("'.$xml->nameFile->__toString().'")
            storePassword "'.$xml->passFile->__toString().'"
            keyAlias "'.$xml->alias->__toString().'"
            keyPassword "'.$xml->passKey->__toString().'"';

            $dirBGradle =  'Projects/'.$_POST['path'].'/app/build.gradle';

            $strfile = file_get_contents($dirBGradle);
            $strfile = str_replace('//keyRealise', $dataKey, $strfile);
            file_put_contents($dirBGradle, $strfile);
        }

        //////////////-------------------------------включение загрузочного экрана и баннера-----------------/////////////////
        
        $dirDimen = 'Projects/'.$_POST['path'].'/app/src/main/res/values/dimens.xml';

        if(!empty($_POST['checkLoad']))
		{
            $xml = simplexml_load_file($dirDimen);

            //включить загрузочную активити
            $xml->integer[4] = 1;
                
            $xml->asXML($dirDimen);

        } else {
            $xml = simplexml_load_file($dirDimen);

            //выключить загрузочную активити
            $xml->integer[4] = 0;
            
            $xml->asXML($dirDimen);
        }

        if(!empty($_POST['checkWeb']))
		{
            $xml = simplexml_load_file($dirDimen);

            //включить загрузочную активити
            $xml->integer[3] = 1;
                
            $xml->asXML($dirDimen);
        } else {
            $xml = simplexml_load_file($dirDimen);

            //выключить загрузочную активити
            $xml->integer[3] = 0;
                
            $xml->asXML($dirDimen);
        }
        
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

    function generateWord($length = 8){
        $chars = 'abdefhiknrstyzABDEFGHKNQRSTYZ';
        $numChars = strlen($chars);
        $string = '';
        for ($i = 0; $i < $length; $i++) {
          $string .= substr($chars, rand(1, $numChars) - 1, 1);
        }
        return $string;
      }

      function search_file($folderName, $fileName, $not_file = false){
        $dir = opendir($folderName); 
        // перебираем папку 
        while (($file = readdir($dir)) !== false){ // перебираем пока есть файлы
            if($file != "." && $file != ".."){ // если это не папка
                if(is_file($folderName."/".$file)){ // если файл проверяем имя
                    // если имя файла нужное, то вернем путь до него
                    if($file == $fileName) return $not_file ? $folderName : $folderName."/".$file;
                } 
                // если папка, то рекурсивно вызываем search_file
                if(is_dir($folderName."/".$file)) return search_file($folderName."/".$file, $fileName, $not_file);
            } 
            
        }
        // закрываем папку
        closedir($dir);
    }
    
