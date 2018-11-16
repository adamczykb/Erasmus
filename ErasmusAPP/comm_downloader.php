<?php
/**
 * Created by PhpStorm.
 * User: julupukki
 * Date: 11.12.17
 * Time: 21:49
 */
	$mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
    $name = $_POST["id"];
    $response = array();
    if ($mysqli->connect_errno) {
        $response["success"] = false;
        echo json_encode($response);
        exit();
    }


    $sql = "SELECT i.ID,i.Desc,i.Date,u.usr_name,u.usr_surname,u.usr_country FROM and_forum_comment i left join usr_info  u on u.usr_id=i.user_id  where i.VISIBLE=1 and i.id_post = '$name' order by i.Date";
    $result = $mysqli->query($sql);
    $array = array();

    // output data of each row
    while($row = $result->fetch_assoc()) {
        $array[]=array(ID=>$row["ID"],Descr=>$row["Desc"],date=>$row["Date"],name=>$row["usr_name"],surname=>$row["usr_surname"],team=>$row["usr_country"]);
    }
    $mysqli->close();
    echo json_encode($array);
    ?>