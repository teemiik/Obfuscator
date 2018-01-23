
<?php

	header('Content-Type: text/html; charset=UTF-8');

   /* if (empty($_POST['path'])) {
        echo 'Не указан путь для сохранения ключа';
        exit;
    }*/

    if(isset($_POST['upload']))
	{   
        if (!empty($_POST['nameFile']) &&  !empty($_POST['passFile']) && !empty($_POST['alias']) 
                        && !empty($_POST['passKey']) && !empty($_POST['FI']) 
                        && !empty($_POST['O']) && !empty($_POST['counter'])) {

            $keyDir = $_POST['path'];
        
            $dirbat = 'create_key.bat';
            $strBuild = 'cd '.$keyDir."\r\n".'keytool -genkey -v -keystore '.$_POST['nameFile'].'.keystore -storepass '.$_POST['passFile'].' -alias '.$_POST['alias'].' -keypass '.$_POST['passKey'].' -dname "CN='.$_POST['FI'].' O='.$_POST['O'].' C='.$_POST['counter'].'" -keyalg RSA -keysize 2048 -validity 10000';
            file_put_contents($dirbat, $strBuild);

            echo exec('create_key.bat');

            $dom = new domDocument("1.0", "utf-8"); // Создаём XML-документ версии 1.0 с кодировкой utf-8
            $keys = $dom->createElement("keys"); // Создаём корневой элемент
            $dom->appendChild($keys);

            $nameFile = $dom->createElement("nameFile", $_POST['nameFile'].'.keystore');
            $passFile = $dom->createElement("passFile", $_POST['passFile']); 
            $alias = $dom->createElement("alias", $_POST['alias']); 
            $passKey = $dom->createElement("passKey", $_POST['passKey']); 

            $keys->appendChild($nameFile);
            $keys->appendChild($passFile);
            $keys->appendChild($alias); 
            $keys->appendChild($passKey);

            if (empty($keyDir)) {
                $dom->save($_POST['nameFile'].'.xml'); // Сохраняем полученный XML-документ в файл
            } else {
                $dom->save($keyDir.'/'.$_POST['nameFile'].'.xml'); // Сохраняем полученный XML-документ в файл
            }

            //system('create_key.bat');
        } else {
            echo "Заполнены не все поля";
        }
    }