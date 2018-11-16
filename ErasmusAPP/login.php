<?php
	$mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
    
    $email = $_POST["email"];
    $passwd = md5($_POST["passwd"]);
    $response = array();
    if ($mysqli->connect_errno) {
    $response["success"] = false;  
    echo json_encode($response);
    exit();
   }
    $stmts = $mysqli->prepare("SELECT a.usr_id,a.usr_name, a.usr_surname, a.usr_country, d.usr_login, a.usr_teach, a.usr_team, a.usr_admin, a.usr_descr, a.usr_fb_page FROM usr_info a left join usr_log d on d.usr_id=a.usr_id  WHERE a.usr_id =(SELECT usr_id FROM usr_log WHERE usr_login ='$email' AND usr_password ='$passwd' AND usr_active = '1')");

    $stmts->execute();
    $stmts->store_result();
    $stmts->bind_result($ID, $Name, $surname,$country,$email ,$teacher,$team,$admin,$descr,$fb);
    
    $response["success"] = false;
    if($stmts->fetch()){
        $response["success"] = true;
    	$response["ID"] = $ID;		
        $response["Name"] = $Name;
        $response["surname"] = $surname;
		$response["email"] = $email;
        $response["team"] = $team;
		$response["country"] = $country;
		$response["teacher"] = $teacher;
        $response["admin"]= $admin;
		$response["descr"]= $descr;
		$response["fb"]= $fb;
    }
    echo json_encode($response);
?>
        		
