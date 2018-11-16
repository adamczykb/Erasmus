<?php
/**
 * Created by PhpStorm.
 * User: julupukki
 * Date: 11.12.17
 * Time: 21:49
 */
	$mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
    $name = $_POST["search"];
    $response = array();
    if ($mysqli->connect_errno) {
        $response["success"] = false;
        echo json_encode($response);
        exit();
    }


    $sql = "SELECT i.ID,i.Title,i.Score,i.owner_id,i.category,i.Descr,i.date,u.usr_name,u.usr_surname,u.usr_team FROM and_forum i left join usr_info u on u.usr_id=i.owner_id where i.Title LIKE '$name%' order by i.date desc";
    $result = $mysqli->query($sql);
    $array = array();

    // output data of each row
    while($row = $result->fetch_assoc()) {
        $array[]=array(ID=>$row["ID"],Title=>$row["Title"],Score=>$row["Score"],owner_id=>$row["owner_id"],category=>$row["category"],Descr=>$row["Descr"],date=>$row["date"],name=>$row["usr_name"],surname=>$row["usr_surname"],team=>$row["usr_team"]);
    }

    $mysqli->close();
    echo json_encode($array);
    ?>