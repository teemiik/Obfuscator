
<?php
	//Устанавливаем кодировку и вывод всех ошибок
	header('Content-Type: text/html; charset=UTF-8');
	
    //Каталог загрузки картинок
    if (!empty($_POST['path'])) {
		$uploadDir = 'Projects/'.$_POST['path'].'/app/src/main/res/drawable';
    } else {
        echo 'Не указана папка с приложением';
        exit;
    }
	
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
	
	if(isset($_POST['upload']))
	{
		if($_FILES['files']['name'] != "")
		{
			var_dump($_FILES['files']);
			if($_FILES['files']['error'] > 0)
				$err[] = $errUpload[$_FILES['files']['error']];
			if(empty($err))
			{
				$type = pathinfo($_FILES['files']['name']);
				$name = $uploadDir .'/image.'. $type['extension'];
                move_uploaded_file($_FILES['files']['tmp_name'],$name);

               $allfile = scandir($uploadDir);

                foreach ($allfile as $value) {
                    if(preg_match('(((.([0-9]))|background)\.(jpg|png|jpeg|JPG|PNG|JPEG))', $value)){
                        unlink($uploadDir.'/'.$value);
                    }
                }
                
                $zip = new ZipArchive;
                $zip->open($name);
                $zip->extractTo($uploadDir.'/');
                $zip->close();
				unlink($name);
				
				if (!empty($_POST['checkParam'])) {
					//формируем батник
					$dirbat = 'build.bat';
					$strBuild = "cd Projects/".$_POST['path']."\ngradlew assembleRelease";
					file_put_contents($dirbat, $strBuild);

					exec('build.bat');
					system('build.bat');
				}
			}
			else {
                echo implode('<br>', $err);
                echo $_POST['path'];
            }
		}

		if($_FILES['webfiles']['name'] != "")
		{		
			
			if($_FILES['webfiles']['error'] > 0)
				$err[] = $errUpload[$_FILES['webfiles']['error']];
			if(empty($err))
			{
				$type = pathinfo($_FILES['webfiles']['name']);
				$name = $uploadDir .'/image.'. $type['extension'];
				move_uploaded_file($_FILES['webfiles']['tmp_name'],$name);

				$allfile = scandir($uploadDir);

                foreach ($allfile as $value) {
                    if(preg_match('(background.*\.(jpg|png|jpeg|JPG|PNG|JPEG))', $value)){
                        unlink($uploadDir.'/'.$value);
                    }
                }
				
                $zip = new ZipArchive;
                $zip->open($name);
                $zip->extractTo($uploadDir.'/');
                $zip->close();
                unlink($name);
				
				if (!empty($_POST['checkParam'])) {
					//формируем батник
					$dirbat = 'build.bat';
					$strBuild = "cd Projects/".$_POST['path']."\ngradlew assembleRelease";
					file_put_contents($dirbat, $strBuild);

					exec('build.bat');
					system('build.bat');
				}
			}
			else {
                echo implode('<br>', $err);
                echo $_POST['path'];
            }
		}

	}
