<?php
    $mysqli = new mysqli("serwer1727017.home.pl", "24939152_0000008", "zserTy3$2s6", "24939152_0000008");
     
    $op = $_POST["op"];
    $ID = $_POST["ID"];
    $user = $_POST["usr_id"];
    $response = array();
   if (mysqli_connect_errno()) {
    $response["success"] = false;  
    echo json_encode($response);
    exit();
   }
   
   
   $result = $mysqli->query("SELECT * from and_forum_raters where ID_post='$ID' AND ID_user='$user'");
   
    if($result->num_rows == 0 AND $ID != 0){
        if($op == '0'){
            
            $mysqli->query("INSERT INTO and_forum_raters (ID_post,ID_user) VALUES ('$ID','$user')");
            $sum=$mysqli->query("SELECT Score from and_forum where ID = '$ID'");
            while($row = $sum->fetch_assoc()) {
            $summ=$row['Score'];
            }
            $summm=intval($summ)+1;
            $mysqli->query("UPDATE and_forum SET Score = '$summm' where ID = '$ID'");
            $response["success"] = true;  
           
        }else if($op == '1'){
            $mysqli->query("INSERT INTO and_forum_raters (ID_post,ID_user) VALUES ('$ID','$user')");
            $sum=$mysqli->query("SELECT Score from and_forum where ID = '$ID'");
            while($row = $sum->fetch_assoc()) {
            $summ=$row['Score'];
            }
            $summm=intval($summ)-1;
            $mysqli->query("UPDATE and_forum SET Score = '$summm' where ID = '$ID'");
            $response["success"] = true;  
        
        }
    }else{
        $response["success"] = false;  
        
    }
  echo json_encode($response);
        exit();
?>