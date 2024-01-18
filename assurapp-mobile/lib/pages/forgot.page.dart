import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:flutter/material.dart';

class ForgotPage extends StatefulWidget {
  const ForgotPage({super.key});

  @override
  State<StatefulWidget> createState() => _ForgotState();

}

class _ForgotState extends State<ForgotPage> {
  TextEditingController emailController = TextEditingController();

  Future<void> forgot() async {
    final String email = emailController.text;

    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/generatepwdCode');

    final String data = json.encode({"username": email});
    final response = await http.post(uri, headers: {'Content-Type': 'application/json',}, body: data,);

    if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("Check you emails to reset password."), backgroundColor: Colors.green,)
        );
        Navigator.pushNamedAndRemoveUntil(context, "/login", (route) => false);
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
                    height: 120,
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  const SizedBox(
                    height: 5,
                  ),
                  const Text(
                    "Forgot your password?",
                    style: TextStyle(fontSize: 14),
                  ),
                  const Text(
                    "** This app is only for experts **",
                    style: TextStyle(
                        fontSize: 10,
                        fontStyle: FontStyle.italic,
                        fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(
                    height: 20,
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
                              height: 20,
                            ),
                            SizedBox(
                              width: double.infinity,
                              child: FilledButton(
                                  onPressed: forgot,
                                  style: ButtonStyle(
                                      padding: MaterialStateProperty.all<EdgeInsets>(
                                          const EdgeInsets.all(18)),
                                      shape: MaterialStateProperty.all<
                                          RoundedRectangleBorder>(
                                          const RoundedRectangleBorder(
                                            borderRadius:
                                            BorderRadius.all(Radius.circular(5)),
                                          ))),
                                  child: Text("Send Email".toUpperCase())),
                            )
                          ],
                        ),
                      )),
                ],
              ),
            ),
          ),
        ));
  }
  
}