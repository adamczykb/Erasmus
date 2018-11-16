<?php
/**
 * Created by PhpStorm.
 * User: julupukki
 * Date: 11.12.17
 * Time: 21:49
 */
	$mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
    $search = $_POST["id"];
    $response = array();
    if ($mysqli->connect_errno) {
        $response["success"] = false;
        echo json_encode($response);
        exit();
    }


    $sql = "SELECT i.usr_id,i.usr_name,i.usr_surname,i.usr_country,i.usr_email,i.usr_teach,i.usr_team,i.usr_descr,i.usr_admin,i.usr_fb_page FROM usr_info i left join usr_log l on i.usr_id=l.usr_id WHERE i.usr_id='$search';";
    $result = $mysqli->query($sql);
    $array = array();

    // output data of each row
    while($row = $result->fetch_assoc()) {
        $array[]=array(id=>$row["usr_id"],Name=>$row["usr_name"],Surname=>$row["usr_surname"],Country=>$row["usr_country"],Email=>$row["usr_email"],Teach=>$row["usr_teach"],Team=>$row["usr_team"],Desc=>$row["usr_descr"],Admin=>$row["usr_admin"], fb=>$row["usr_fb_page"]);
    }

    $mysqli->close();
    echo json_encode($array);
    ?>