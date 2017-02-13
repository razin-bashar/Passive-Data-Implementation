<?php
 $json = file_get_contents('php://input');
    $obj = json_decode($json);
    //print_r($obj);
    //print_r("this is a test");
    $Name = $obj->{'Name'};
    $Email=$obj->{'Email'};
    $Password=$obj->{'Password'};
/**
 * @author Ravi Tamada
 * @link http://www.androidhive.info/2012/01/android-login-and-registration-with-php-mysql-and-sqlite/ Complete tutorial
 */

require_once 'DB_Functions.php';
$db = new DB_Functions();
// json response array
$response = array("error" => FALSE);

if ($Name && $Email && $Password) {

    // receiving the post params
    $name =$Name;
    $email =$Email;
    $password =$Password;

    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = "User already existed with " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name, $email, $password);
        if ($user) {
            // user stored successfully
            $response["error"] = 'No error';           
            $response["Name"] = $user["Name"];
            $response["Email"] = $user["Email"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>

