<?php
    $mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
     
    $name = $_POST["Name"];
    $surname = $_POST["surname"];
    $email = $_POST["email"];
	$team = $_POST["team"];
    $passwd = md5($_POST["passwd"]);
	$country = $_POST["country"];
	$teacher = $_POST["teacher"];
	$passwd = $passwd;
    $response = array();
   if (mysqli_connect_errno()) {
    $response["success"] = false;  
    echo json_encode($response);
    exit();
   }
   
   
    $mysqli->query("INSERT INTO usr_info (usr_name, usr_surname,usr_country ,usr_email, usr_teach,usr_team) VALUES ('$name', '$surname','$country','$email','$teacher','$team' )");
    if($mysqli->affected_rows<1){
        $response["success"] = false;  
        echo json_encode($response);
        exit();
    }
    
    $mysqli->query("update usr_log set usr_login='$email' , usr_password='$passwd'  where usr_id=(SELECT usr_id from usr_info order by usr_id desc limit 1)");
    if($mysqli->affected_rows<1){
        $response["success"] = false;  
        echo json_encode($response);
        exit();
    }
    
    
    $response["success"] = true;  
    
    echo json_encode($response);
?>							