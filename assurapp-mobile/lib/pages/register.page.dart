import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class RegisterPage extends StatefulWidget {
  const RegisterPage({super.key});

  @override
  State<StatefulWidget> createState() => _RegisterState();

}

class _RegisterState extends State<RegisterPage> {
  TextEditingController firstnameController = TextEditingController();
  TextEditingController lastnameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController passwordCController = TextEditingController();
  bool visible = true;

  void toggleVisible() {
    setState(() {
      visible = !visible;
    });
  }

  Future<void> register() async {
    final String firstname = firstnameController.text;
    final String lastname = lastnameController.text;
    final String email = emailController.text;
    final String password = passwordController.text;

    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/register');

    final String data = json.encode({"id": 0, "name": firstname, "lastname": lastname, "email": email, "password": password, "role": {"id": 3, "label": "EXPERT"}});
    final response = await http.post(uri, headers: {'Content-Type': 'application/json',}, body: data,);

    if (response.statusCode == 200) {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("You have registered successfully."), backgroundColor: Colors.green,)
      );
      SharedPreferences prefs = await SharedPreferences.getInstance();
      await prefs.setString('verifyEmail', email);
      Navigator.pushNamedAndRemoveUntil(context, "/code", (route) => false);
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
                const SizedBox(height: 20,),
                const Image(image: AssetImage("assets/images/icon.png"), height: 100,),
                const SizedBox(height: 20,),
                Text("Get On Board".toUpperCase(), style: const TextStyle(fontSize: 28, fontWeight: FontWeight.bold),),
                const SizedBox(height: 5,),
                const Text("Create your profile", style: TextStyle(fontSize: 14),),
                const Text("** This app is only for experts **", style: TextStyle(fontSize: 10, fontStyle: FontStyle.italic, fontWeight: FontWeight.bold),),
                Form(
                    child: Container(
                      padding: const EdgeInsets.symmetric(vertical: 10),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          TextFormField(
                            controller: firstnameController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.person_outline_outlined),
                                labelText: "First Name",
                                hintText: "John",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: lastnameController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.person_outline_outlined),
                                labelText: "Last Name",
                                hintText: "Doe",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: emailController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.alternate_email_outlined),
                                labelText: "Email",
                                hintText: "expert@example.com",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
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
                                )
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: passwordCController,
                            obscureText: true,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.fingerprint),
                                labelText: "Confirm Password",
                                hintText: "************",
                                border: OutlineInputBorder(),
                            ),
                          ),
                          const SizedBox(height: 10,),
                          SizedBox(
                            width: double.infinity,
                            child:  FilledButton(
                              onPressed: () => {
                                if (passwordCController.text != passwordController.text) {
                                  ScaffoldMessenger.of(context).showSnackBar(
                                  const SnackBar(content: Text("Password are different."), backgroundColor: Colors.red,)
                                  )} else register()
                                },
                              style: ButtonStyle(
                                  padding: MaterialStateProperty.all<EdgeInsets>(
                                      const EdgeInsets.all(18)
                                  ),
                                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                      const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(5)),)
                                  )
                              ),
                              child: Text("Sign Up".toUpperCase()),
                            ),
                          )
                        ],
                      ),
                    )
                ),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    const Text("OR"),
                    const SizedBox(height: 5,),
                    SizedBox(
                      width: double.infinity,
                      child: OutlinedButton.icon(
                          onPressed: () => {},
                          style: ButtonStyle(
                              padding: MaterialStateProperty.all<EdgeInsets>(
                                  const EdgeInsets.all(18)
                              ),
                              shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                  const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(5)),)
                              )
                          ),
                          icon: const Image(image: AssetImage("assets/images/google.png"), width: 20,),
                          label: const Text("Sign In with Google")
                      ),
                    ),
                    const SizedBox(height: 5,),
                    TextButton(
                        onPressed: () => Navigator.pushNamedAndRemoveUntil(context, "/login", (route) => false),
                        child: const Text.rich(
                            TextSpan(
                                text: "Already have account? ",
                                style: TextStyle(color: Colors.black),
                                children:  [
                                  TextSpan(
                                    text: "Log In",
                                    style: TextStyle(color: Colors.blue),
                                  )
                                ]
                            )
                        )
                    ),
                  ],
                )
              ],
            ),
          ),
        ),
      )
    );
  }
}