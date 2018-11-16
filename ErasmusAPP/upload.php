    <?php
    $mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
    $dir = 'uploads/';
    $max_file_size = 30000000;
    $change_name = false; $name_length = 55;

    if(!file_exists($dir)) exit('Katalog '.$dir.' nie istnieje!');

    if($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['upload'])) {

        $tmp_name = $_FILES['userfile']['tmp_name'];
        $name = $_FILES['userfile']['name'];
        $type = $_FILES['userfile']['type'];
        $size = $_FILES['userfile']['size'];
        $error = $_FILES['userfile']['error'];
       
        $explode_name = explode('.',$name);
        $extension = @$explode_name[1];
$date = date('H:i:s_d.m.Y_');
       
        if($change_name) {
            $name = $explode_name[0];
            $new_name = substr(md5($name),0,$name_length).'.'.$extension;

            $path = $dir.$date.$new_name;
        }
        else {
            $path = $dir.$date.$name;
        }
       
        $dirname = dirname($_SERVER['SCRIPT_NAME']) == '/' || dirname($_SERVER['SCRIPT_NAME']) == '\\' ? null : dirname($_SERVER['SCRIPT_NAME']);
       
        $full_path = 'http://'.$_SERVER['HTTP_HOST'].$dirname.'/'.$path;
       
        if($error == UPLOAD_ERR_NO_FILE) {
            echo '';
        }
        elseif($error == UPLOAD_ERR_PARTIAL) {
            echo 'Błąd! Plik został tylko częściowo załadowany.';
        }
        elseif($error == UPLOAD_ERR_NO_TMP_DIR) {
            echo 'Błąd! Brak folderu tymczasowego.';
        }
        elseif($error == UPLOAD_ERR_INI_SIZE) {
            echo 'Błąd! Plik jest za duży dla serwera.';
        }
        elseif($size > $max_file_size) {
            echo 'Za duży plik.';
        }
        else {
       
            if(is_uploaded_file($tmp_name)) {
           
                if(move_uploaded_file($tmp_name,$path)) {
                    echo 'Plik został wysłany. <br /><a href="'.$full_path.'">'.$full_path.'</a><br><br>';
                }
                else {
                    echo 'Nie udało się wysłać pliku. Spróbuj później.';
                }
         
            }
            else {
                echo 'Co ty próbujesz.';
            }
       
        }
           
    }

    ?>
    <html>
    
    <body bgcolor="#CACACA">
    <center>
    <table width="85%" height="85%">
    <tr>
    <td align="center" valign="middle">
    
    
    <center>
    <form action="<?php echo $_SERVER['SCRIPT_NAME']; ?>" method="post" enctype="multipart/form-data">
        <input type="file" name="userfile" style="color: #FFFFFF; background-color: #EB0000; width: 300px; border-style: solid; border-color: #EB0000; border-width: 10px"/><br><br>
        <button type="submit" name="upload" style="color: #FFFFFF; background-color: #EB0000; border-style: solid; border-color: #EB0000; width: 300px; border-width: 10px">Send</button>
    </form>
    </center>
    
    
    </td>
    </tr>
    </table>	