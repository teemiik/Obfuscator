
<?php	
    //Каталог загрузки картинок
    if (isset($_POST['app'])) {
		header('Location: http://'. $_SERVER['HTTP_HOST'] .'/android/application_change.html');
		exit;
	} else if (isset($_POST['picture'])) {
		header('Location: http://'. $_SERVER['HTTP_HOST'] .'/android/pictures.html');
		exit;
	} else if (isset($_POST['obfuscator'])) {
		header('Location: http://'. $_SERVER['HTTP_HOST'] .'/android/obfuscator.html');
		exit;
	} else if (isset($_POST['manifest'])) {
		header('Location: http://'. $_SERVER['HTTP_HOST'] .'/android/manifest.html');
		exit;
	} else {
        echo 'Пустой запрос';
        exit;
    }
