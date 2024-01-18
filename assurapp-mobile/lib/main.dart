import 'package:flutter/material.dart';
import 'package:assurapp/pages/code.page.dart';
import 'package:assurapp/pages/register.page.dart';
import 'package:assurapp/pages/login.page.dart';
import 'package:assurapp/pages/profile.page.dart';
import 'package:assurapp/pages/claims.page.dart';
import 'package:assurapp/pages/expertises.page.dart';
import 'package:assurapp/pages/forgot.page.dart';

import 'pages/expertise.page.dart';

void main() async => runApp(const AssurApp());


class AssurApp extends StatelessWidget {
  const AssurApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      initialRoute: '/',
      routes: {
        '/': (context) => const ProfilePage(),
        '/claims': (context) => const ClaimsPage(),
        '/expertise': (context) => const ExpertisePage(),
        '/expertises': (context) => const ExpertisesPage(),
        '/login': (context) => const LoginPage(),
        '/register': (context) => const RegisterPage(),
        '/code': (context) => const CodePage(),
        '/forgot': (context) => const ForgotPage(),
      },
      theme: ThemeData(colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue)),
    );
  }
}