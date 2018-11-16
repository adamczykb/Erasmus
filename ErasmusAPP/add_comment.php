<?php
    $mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
     
    $ID = $_POST["ID_post"];
    $title= $_POST["txt"];
    $desc = $_POST["id_usr"];

    $response = array();
   if (mysqli_connect_errno()) {
    $response["success"] = false;  
    echo json_encode($response);
    exit();
   }
   
   
    $mysqli->query("INSERT INTO and_forum_comment (`Desc`, `user_id`, `id_post`) VALUES ('$title', '$desc','$ID')");
    if($mysqli->affected_rows<1){
        $response["success"] = false;  
        echo json_encode($response);
        exit();
    }    
    $response["success"] = true;  
    
    echo json_encode($response);
?>			