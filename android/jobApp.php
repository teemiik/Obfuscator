
<?php

    //Устанавливаем кодировку и вывод всех ошибок
	header('Content-Type: text/html; charset=UTF-8');

    if (empty($_POST['path'])) {
        echo 'Не указана папка с приложением';
        exit;
    }

	if(isset($_POST['upload']))
	{   
            //Вывод ошибок
        $err = array();
        
        $errUpload = array( 
                            0 => 'Ошибок не возникло, файл был успешно загружен на сервер. ', 
                            1 => 'Размер принятого файла превысил максимально допустимый размер, который задан директивой upload_max_filesize конфигурационного файла php.ini.', 
                            2 => 'Размер загружаемого файла превысил значение MAX_FILE_SIZE, указанное в HTML-форме.', 
                            3 => 'Загружаемый файл был получен только частично.', 
                            4 => 'Файл не был загружен.', 
                            6 => 'Отсутствует временная папка. Добавлено в PHP 4.3.10 и PHP 5.0.3.' 
                        ); 

        //замена icon приложения
        $uploadDir = 'Projects/'.$_POST['path'].'/app/src/main/res';
        if(!empty($_FILES))
		{
			if($_FILES['files']['error'] > 0)
				$err[] = $errUpload[$_FILES['files']['error']];
			if(empty($err))
			{
				$type = pathinfo($_FILES['files']['name']);
				$name = $uploadDir .'/ic_launcher.'. $type['extension'];
                move_uploaded_file($_FILES['files']['tmp_name'],$name);

                $zip = new ZipArchive;
                $zip->open($name);
                $zip->extractTo($uploadDir.'/');
                $zip->close();
                unlink($name);

                $allfile = scandir($uploadDir);

                foreach ($allfile as $value) {
                    if(preg_match('(\.png)', $value)){
                        $sizeImage = getimagesize($uploadDir.'/'.$value);
                        if ($sizeImage[0] == 192) {
                            copy($uploadDir.'/'.$value, $uploadDir.'/mipmap-xxxhdpi/ic_launcher.png');
                        } else if ($sizeImage[0] == 144) {
                            copy($uploadDir.'/'.$value, $uploadDir.'/mipmap-xxhdpi/ic_launcher.png');
                        } else if ($sizeImage[0] == 96) {
                            copy($uploadDir.'/'.$value, $uploadDir.'/mipmap-xhdpi/ic_launcher.png');
                        } else if ($sizeImage[0] == 48) {
                            copy($uploadDir.'/'.$value, $uploadDir.'/mipmap-mdpi/ic_launcher.png');
                        } else if ($sizeImage[0] == 72) {
                            copy($uploadDir.'/'.$value, $uploadDir.'/mipmap-hdpi/ic_launcher.png');
                        }
                    }
                }

                foreach ($allfile as $value) {
                    if(preg_match('(\.png)', $value)){
                        unlink($uploadDir.'/'.$value);
                    }
                }
            } 
        }

        //замена ссылки на api
        if (!empty($_POST['base_url']) && !empty($_POST['url'])) {
            $dirString = 'Projects/'.$_POST['path'].'/app/src/main/res/values/strings.xml'; 
            if (file_exists($dirString)) {
                $xml = simplexml_load_file($dirString);

                //смена api_base_url
                $xml->string[15] = $_POST['base_url'];

                //смена api_url
                $xml->string[16] = $_POST['url'];
                
                $xml->asXML($dirString);
            } else {
                exit('Не удалось открыть файл string.xml в проекте');
                exit;
            }
        }

        //замена ссылки на api
        if (!empty($_POST['web_base_url']) && !empty($_POST['web_url'])) {
            $dirString = 'Projects/'.$_POST['path'].'/app/src/main/res/values/strings.xml'; 
            if (file_exists($dirString)) {
                $xml = simplexml_load_file($dirString);

                //смена api_base_url
                $xml->string[3] = $_POST['web_base_url'];

                //смена api_url
                $xml->string[4] = $_POST['web_url'];
                
                $xml->asXML($dirString);
            } else {
                exit('Не удалось открыть файл string.xml в проекте');
                exit;
            }
        }

        if (!empty($_POST['com']) && !empty($_POST['company']) && !empty($_POST['name_app'])) {
            //смена имен директорий
            //com
            $buildOldPath = "";

            $dirname = "Projects/".$_POST['path'].'/app/src/main/java'; //корневая директория
            $newdirname = $dirname.'/'.$_POST['com'];
            $dirname_ar = scandir($dirname);

            //echo $dirname_ar;

            foreach($dirname_ar as $value) {
                if (!is_dir($value)) {
                    $dirChange = $value;
                }
            }
            $buildOldPath = $dirChange;
            rename($dirname.'/'.$dirChange, $newdirname);

            //user or company
            $newdirnameuser = $newdirname.'/'.$_POST['company'];
            $dircompany = scandir($newdirname);

            foreach($dircompany as $value) {
                if (!is_dir($value)) {
                    $dirChange = $value;
                }
            }

            $buildOldPath = $buildOldPath.'.'.$dirChange;
            rename($newdirname.'/'.$dirChange, $newdirnameuser);

            //app
            $newdirnameapp = $newdirnameuser.'/'.$_POST['name_app'];
            $dirapp = scandir($newdirnameuser);

            foreach($dirapp as $value) {
                if (!is_dir($value)) {
                    $dirChange = $value;
                }
            }

            $buildOldPath = $buildOldPath.'.'.$dirChange;
            rename($newdirnameuser.'/'.$dirChange, $newdirnameapp);

            //смена имени пакета в классах
            $dircalss = $newdirnameapp;
            $buildPath = $_POST['com'].'.'.$_POST['company'].'.'.$_POST['name_app'];
            $allclass = scandir($dircalss);

            foreach ($allclass as $value) {
                if(!is_dir($value)) {
                    $strfile = file_get_contents($dircalss."/".$value);
                    $str_replace = str_replace($buildOldPath, $buildPath, $strfile);
                    file_put_contents($dircalss."/".$value, $str_replace);
                }
            }

            //смена имени пакета в манифесте
            $dirmanifest = 'Projects/'.$_POST['path'].'/app/src/main/AndroidManifest.xml';
            $strfile = file_get_contents($dirmanifest);
            $str_replace = str_replace($buildOldPath, $buildPath, $strfile);
            file_put_contents($dirmanifest, $str_replace);

            //смена имени пакета в файле gradle
            $dirgradle = 'Projects/'.$_POST['path'].'/app/build.gradle';
            $strfile = file_get_contents($dirgradle);
            $str_replace = str_replace($buildOldPath, $buildPath, $strfile);
            file_put_contents($dirgradle, $str_replace);

        } 
        else if (!empty($_FILES)) {} 
        else if (!empty($_POST['base_url']) && !empty($_POST['url'])) {} 
        else {
            echo 'Вы заполнили не все поля';
            exit;
        }

        //выставляем тайминги
        $dirDimen = 'Projects/'.$_POST['path'].'/app/src/main/res/values/dimens.xml';
        if (!empty($_POST['krest'])) {
              
            if (file_exists($dirDimen)) {
                $xml = simplexml_load_file($dirDimen);

                $xml->integer[0] = $_POST['krest'];
                
                $xml->asXML($dirDimen);
            }
        }

        if (!empty($_POST['auto'])) {
              
            if (file_exists($dirDimen)) {
                $xml = simplexml_load_file($dirDimen);

                $xml->integer[1] = $_POST['auto'];
                
                $xml->asXML($dirDimen);
            }
        }

        if (!empty($_POST['delay'])) {
              
            if (file_exists($dirDimen)) {
                $xml = simplexml_load_file($dirDimen);

                $xml->integer[2] = $_POST['delay'];
                
                $xml->asXML($dirDimen);
            }
        }

        if (!empty($_POST['close'])) {
              
            if (file_exists($dirDimen)) {
                $xml = simplexml_load_file($dirDimen);

                $xml->integer[6] = $_POST['close'];
                
                $xml->asXML($dirDimen);
            }
        }

        if (!empty($_POST['checkCompile'])) {
            //формируем батник
            $dirbat = 'build.bat';
            $strBuild = "cd Projects/".$_POST['path']."\r\ngradlew assembleRelease";
            file_put_contents($dirbat, $strBuild);

            exec('build.bat');
            system('build.bat');
        }
    }
