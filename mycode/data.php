<?php

 $json = file_get_contents('php://input');
    $obj = json_decode($json);
   // print_r($obj);
   // print_r("this is a test");
    $SID = $obj->{'SID'};


$con=mysqli_connect("localhost","root","","passive");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$sql="SELECT * FROM Subscription WHERE SID='$SID'";
$Bloodgroup=NULL;
$Location=NULL;
if ($result=mysqli_query($con,$sql))
  {
  // Fetch one and one row
  while ($row=mysqli_fetch_row($result))
    {
    
    $Bloodgroup=$row[1];
    $Location=$row[3];
    }
  // Free result set
  mysqli_free_result($result);
}
$sql2="SELECT * from Data WHERE Location = UPPER('$Location') and BloodGroup = UPPER('$Bloodgroup')";
if ($result=mysqli_query($con,$sql2))
  {
  // Fetch one and one row
  $solution[]=array();
  $i=1;
  while ($row=mysqli_fetch_row($result))
    {
	// Full texts 	Did 	Name 	Location 	BloodGroup 	Time 	Phone
    	$arr = array('Did'=>$row[0],'Name' => $row[1], 'Location' => $row[2], 'BloodGroup' => $row[3], 'Phone' => $row[5]);
    	//$encoderow=json_encode($arr);
	$solution[$i]=$arr;
        $i=$i+1;
    }
  $encodeall=json_encode($solution);
  echo $encodeall;
  // Free result set
  mysqli_free_result($result);
}
mysqli_close($con);
?>

