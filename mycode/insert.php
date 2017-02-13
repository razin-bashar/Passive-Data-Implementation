<?php
 $json = file_get_contents('php://input');
    $obj = json_decode($json);
    //print_r($obj);
    //print_r("this is a test");
    $Name = $obj->{'Name'};
    $Location=$obj->{'Location'};
    $BloodGroup=$obj->{'BloodGroup'};
    $Phone=$obj->{'Phone'};
/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

require_once 'DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);
//Did 	Name 	Location 	BloodGroup 	Time 	Phone
if ($Name && $Location && $BloodGroup && $Phone) {

     if ($db->isdataExisted($Name, $Location, $BloodGroup,$Phone)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "Data already existed with " . $Name;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeData($Name, $Location, $BloodGroup,$Phone);
        if ($user) {
            // user stored successfully
            $response["error"] ='No error';           
            $response["Success"] ='Successful';
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in Insertion!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (Name 	Location 	BloodGroup 	Phone) is missing!";
    echo json_encode($response);
}
?>

