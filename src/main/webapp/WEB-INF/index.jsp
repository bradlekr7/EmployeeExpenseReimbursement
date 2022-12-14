<!DOCTYPE html>
<html>

<head>
  <style>


</style>
  <title>Login Page web inf</title>


  <!--Bootstrap-->
  <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
        integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
        crossorigin="anonymous">

  <!--Custom styles-->
  <link rel="stylesheet" type="text/css" href="./css/styles.css">

</head>

<body>
<div class="wrapper" id="hero_container">
  <form class="form-signin" id="login" action="login" method="POST">
    <h2 class="form-signin-heading">Please login</h2>
    <input type="text" class="form-control" name="username"
           placeholder="Username" required="" autofocus="" /> <input
          type="password" class="form-control" name="password"
          placeholder="Password" required="" />
    <br>
    <select name="positionType" class="form-control">
      <option value="Select a Position" selected>Select a Position</option>
      <option value="Employee">Employee</option>
      <option value="Manager">Manager</option>
    </select>
    <br> <br>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
  </form>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script
        src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>


</body>

</html>