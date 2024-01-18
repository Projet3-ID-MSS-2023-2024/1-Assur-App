import 'dart:convert';

import 'package:http/http.dart' as http;

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:shared_preferences/shared_preferences.dart';

class CodePage extends StatefulWidget {
  const CodePage({super.key});

  @override
  State<StatefulWidget> createState() => _CodeState();

}

class _CodeState extends State<CodePage> {
  TextEditingController code1Controller = TextEditingController();
  TextEditingController code2Controller = TextEditingController();
  TextEditingController code3Controller = TextEditingController();
  TextEditingController code4Controller = TextEditingController();
  TextEditingController code5Controller = TextEditingController();
  TextEditingController code6Controller = TextEditingController();

  Future<void> activation() async {

    final String code1 = code1Controller.text;
    final String code2 = code2Controller.text;
    final String code3 = code3Controller.text;
    final String code4 = code4Controller.text;
    final String code5 = code5Controller.text;
    final String code6 = code6Controller.text;

    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/verifyAccount');
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? email = prefs.getString('verifyEmail');
    final String code = code1 + code2 + code3 + code4 + code5 + code6;
    final String data = json.encode({"username": email, "code": code.toString()});
    final response = await http.post(uri, headers: {'Content-Type': 'application/json',}, body: data,);

    if (response.statusCode == 200) {
        const SnackBar(content: Text("You are redirected to login page."), backgroundColor: Colors.green,);
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
                    height: 50,
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  Text(
                    "Activation Code".toUpperCase(),
                    style:
                    const TextStyle(fontSize: 28, fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(
                    height: 5,
                  ),
                  const Text(
                    "Check your emails for code",
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
                    height: 30,
                  ),
                  Form(
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 10),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                SizedBox(
                                  height: 40,
                                  width: 40,
                                  child: TextField(
                                    controller: code1Controller,
                                    onChanged: (value) => {
                                     if (value.length == 1) FocusScope.of(context).nextFocus()
                                    },
                                    decoration: const InputDecoration(hintText: '0'),
                                    style: Theme.of(context).textTheme.headlineSmall,
                                    keyboardType: TextInputType.number,
                                    textAlign: TextAlign.center,
                                    inputFormatters: [
                                      LengthLimitingTextInputFormatter(1),
                                      FilteringTextInputFormatter.digitsOnly
                                    ],
                                  ),
                                ),
                                SizedBox(
                                  height: 40,
                                  width: 40,
                                  child: TextField(
                                    controller: code2Controller,
                                    onChanged: (value) => {
                                      if (value.length == 1) FocusScope.of(context).nextFocus()
                                    },
                                    decoration: const InputDecoration(hintText: '0'),
                                    style: Theme.of(context).textTheme.headlineSmall,
                                    keyboardType: TextInputType.number,
                                    textAlign: TextAlign.center,
                                    inputFormatters: [
                                      LengthLimitingTextInputFormatter(1),
                                      FilteringTextInputFormatter.digitsOnly
                                    ],
                                  ),
                                ),
                                SizedBox(
                                  height: 40,
                                  width: 40,
                                  child: TextField(
                                    controller: code3Controller,
                                    onChanged: (value) => {
                                      if (value.length == 1) FocusScope.of(context).nextFocus()
                                    },
                                    decoration: const InputDecoration(hintText: '0'),
                                    style: Theme.of(context).textTheme.headlineSmall,
                                    keyboardType: TextInputType.number,
                                    textAlign: TextAlign.center,
                                    inputFormatters: [
                                      LengthLimitingTextInputFormatter(1),
                                      FilteringTextInputFormatter.digitsOnly
                                    ],
                                  ),
                                ),
                                SizedBox(
                                  height: 40,
                                  width: 40,
                                  child: TextField(
                                    controller: code4Controller,
                                    onChanged: (value) => {
                                      if (value.length == 1) FocusScope.of(context).nextFocus()
                                    },
                                    decoration: const InputDecoration(hintText: '0'),
                                    style: Theme.of(context).textTheme.headlineSmall,
                                    keyboardType: TextInputType.number,
                                    textAlign: TextAlign.center,
                                    inputFormatters: [
                                      LengthLimitingTextInputFormatter(1),
                                      FilteringTextInputFormatter.digitsOnly
                                    ],
                                  ),
                                ),
                                SizedBox(
                                  height: 40,
                                  width: 40,
                                  child: TextField(
                                    controller: code5Controller,
                                    onChanged: (value) => {
                                      if (value.length == 1) FocusScope.of(context).nextFocus()
                                    },
                                    decoration: const InputDecoration(hintText: '0'),
                                    style: Theme.of(context).textTheme.headlineSmall,
                                    keyboardType: TextInputType.number,
                                    textAlign: TextAlign.center,
                                    inputFormatters: [
                                      LengthLimitingTextInputFormatter(1),
                                      FilteringTextInputFormatter.digitsOnly
                                    ],
                                  ),
                                ),
                                SizedBox(
                                  height: 40,
                                  width: 40,
                                  child: TextField(
                                    controller: code6Controller,
                                    onChanged: (value) => {
                                      if (value.length == 1) FocusScope.of(context).nextFocus()
                                    },
                                    decoration: const InputDecoration(hintText: '0'),
                                    style: Theme.of(context).textTheme.headlineSmall,
                                    keyboardType: TextInputType.number,
                                    textAlign: TextAlign.center,
                                    inputFormatters: [
                                      LengthLimitingTextInputFormatter(1),
                                      FilteringTextInputFormatter.digitsOnly
                                    ],
                                  ),
                                ),
                              ],
                            ),
                            const SizedBox(
                              height: 30,
                            ),
                            SizedBox(
                              width: double.infinity,
                              child: FilledButton(
                                  onPressed: activation,
                                  style: ButtonStyle(
                                      padding: MaterialStateProperty.all<EdgeInsets>(
                                          const EdgeInsets.all(18)),
                                      shape: MaterialStateProperty.all<
                                          RoundedRectangleBorder>(
                                          const RoundedRectangleBorder(
                                            borderRadius:
                                            BorderRadius.all(Radius.circular(5)),
                                          ))),
                                  child: Text("Verify".toUpperCase())),
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