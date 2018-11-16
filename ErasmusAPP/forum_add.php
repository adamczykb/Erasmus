<?php
    $mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
     
    $ID = $_POST["ID"];
    $title= $_POST["title"];
    $desc = $_POST["desc"];
$category = $_POST["category"];
    $response = array();
   if (mysqli_connect_errno()) {
    $response["success"] = false;  
    echo json_encode($response);
    exit();
   }
   
   
    $mysqli->query("INSERT INTO and_forum(Title,owner_id,category, Descr) VALUES ('$title', '$ID','$category','$desc')");
    if($mysqli->affected_rows<1){
        $response["success"] = false;  
        echo json_encode($response);
        exit();
    }    
    $response["success"] = true;  
    
    echo json_encode($response);
?>			