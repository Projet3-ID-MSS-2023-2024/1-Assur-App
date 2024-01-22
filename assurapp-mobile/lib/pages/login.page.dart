import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:jwt_decode/jwt_decode.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<StatefulWidget> createState() => _LoginState();

}

class _LoginState extends State<LoginPage> {
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  bool visible = true;

  void toggleVisible() {
    setState(() {
      visible = !visible;
    });
  }

  Future<void> login() async {
    final String email = emailController.text;
    final String password = passwordController.text;

    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/login');

    final String data = json.encode({"username": email, "password": password});
    final response = await http.post(uri, headers: {'Content-Type': 'application/json',}, body: data,);

    if (response.statusCode == 200) {
      String token = json.decode(response.body)['bearer'];
      Map<String, dynamic> decodedToken = Jwt.parseJwt(token);
      String role = decodedToken['role']['label'] ?? 'Unknown Role';
      if (role != "EXPERT") {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("This app is only for experts please visit our website."), backgroundColor: Colors.red,)
        );
      } else {
        SharedPreferences prefs = await SharedPreferences.getInstance();
        await prefs.setInt('id', decodedToken['id']);
        await prefs.setString('email', decodedToken['sub']);
        await prefs.setString('role', decodedToken['role']['label']);
        await prefs.setString('token', token);
        Navigator.pushNamedAndRemoveUntil(context, "/", (route) => false);
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("An error occurred please check your data or network."), backgroundColor: Colors.red,)
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
      child: SingleChildScrollView(
        child: Container(
          padding: const EdgeInsets.all(10),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const SizedBox(
                height: 50,
              ),
              const Image(
                image: AssetImage("assets/images/icon.png"),
                height: 100,
              ),
              const SizedBox(
                height: 20,
              ),
              Text(
                "Welcome Back".toUpperCase(),
                style:
                    const TextStyle(fontSize: 28, fontWeight: FontWeight.bold),
              ),
              const SizedBox(
                height: 5,
              ),
              const Text(
                "Log In to your expert dashboard",
                style: TextStyle(fontSize: 14),
              ),
              const Text(
                "** This app is only for experts **",
                style: TextStyle(
                    fontSize: 10,
                    fontStyle: FontStyle.italic,
                    fontWeight: FontWeight.bold),
              ),
              Form(
                  child: Container(
                padding: const EdgeInsets.symmetric(vertical: 10),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    TextFormField(
                      controller: emailController,
                      decoration: const InputDecoration(
                          prefixIcon: Icon(Icons.alternate_email_outlined),
                          labelText: "Email",
                          hintText: "expert@example.com",
                          border: OutlineInputBorder()),
                    ),
                    const SizedBox(
                      height: 10,
                    ),
                    TextFormField(
                      controller: passwordController,
                      obscureText: visible,
                      decoration: InputDecoration(
                          prefixIcon: const Icon(Icons.fingerprint),
                          labelText: "Password",
                          hintText: "************",
                          border: const OutlineInputBorder(),
                          suffixIcon: IconButton(
                            icon: Icon(visible ? Icons.remove_red_eye_sharp: Icons.remove_red_eye_outlined),
                            onPressed: toggleVisible,
                          )),
                    ),
                    Align(
                      alignment: Alignment.centerRight,
                      child: TextButton(
                        onPressed: () => Navigator.pushNamedAndRemoveUntil(context, "/forgot", (route) => false),
                        child: const Text(
                          "Forget Password ?",
                          style: TextStyle(color: Colors.blue),
                        ),
                      ),
                    ),
                    SizedBox(
                      width: double.infinity,
                      child: FilledButton(
                          onPressed: login,
                          style: ButtonStyle(
                              padding: MaterialStateProperty.all<EdgeInsets>(
                                  const EdgeInsets.all(18)),
                              shape: MaterialStateProperty.all<
                                      RoundedRectangleBorder>(
                                  const RoundedRectangleBorder(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(5)),
                              ))),
                          child: Text("Login".toUpperCase())),
                    )
                  ],
                ),
              )),
              Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  const Text("OR"),
                  const SizedBox(
                    height: 5,
                  ),
                  SizedBox(
                    width: double.infinity,
                    child: OutlinedButton.icon(
                        onPressed: () => {},
                        style: ButtonStyle(
                            padding: MaterialStateProperty.all<EdgeInsets>(
                                const EdgeInsets.all(18)),
                            shape: MaterialStateProperty.all<
                                    RoundedRectangleBorder>(
                                const RoundedRectangleBorder(
                              borderRadius:
                                  BorderRadius.all(Radius.circular(5)),
                            ))),
                        icon: const Image(
                          image: AssetImage("assets/images/google.png"),
                          width: 20,
                        ),
                        label: const Text("Sign In with Google")),
                  ),
                  const SizedBox(
                    height: 5,
                  ),
                  TextButton(
                      onPressed: () =>
                          Navigator.pushNamedAndRemoveUntil(context, "/register", (route) => false),
                      child: const Text.rich(TextSpan(
                          text: "Don't have account? ",
                          style: TextStyle(color: Colors.black),
                          children: [
                            TextSpan(
                              text: "Sign Up",
                              style: TextStyle(color: Colors.blue),
                            )
                          ]))),
                ],
              )
            ],
          ),
        ),
      ),
    ));
  }
}
