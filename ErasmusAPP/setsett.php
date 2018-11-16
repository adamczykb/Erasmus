<?php
    $mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
     
	$id = $_POST["id"]; 
    $name = $_POST["name"];
    $surname = $_POST["surname"];
    $email = $_POST["email"];
	$desc= $_POST["desc"];
	$fb= $_POST["fb"];
	$team = $_POST["func"];
    $response = array();
   if (mysqli_connect_errno()) {
    $response["success"] = false;  
    echo json_encode($response);
    exit();
   }
   
   
    $mysqli->query("UPDATE usr_info set usr_name='$name', usr_surname='$surname', usr_email='$email' ,usr_team='$team',usr_descr='$desc',usr_fb_page='$fb' where usr_id='$id'");
    if($mysqli->affected_rows<1){
        $response["success"] = false;  
        echo json_encode($response);
        exit();
    }
	$mysqli->query("UPDATE usr_log set usr_login='$email' where usr_id='$id'");
    $response["success"] = true;  
    
    echo json_encode($response);
?>							