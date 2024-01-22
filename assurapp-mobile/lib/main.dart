import 'dart:io';

import 'package:assurapp/pages/expertise.show.page.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:assurapp/pages/code.page.dart';
import 'package:assurapp/pages/register.page.dart';
import 'package:assurapp/pages/login.page.dart';
import 'package:assurapp/pages/profile.page.dart';
import 'package:assurapp/pages/claims.page.dart';
import 'package:assurapp/pages/expertises.page.dart';
import 'package:assurapp/pages/forgot.page.dart';

import 'pages/expertise.page.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  Platform.isAndroid
      ? await  Firebase.initializeApp(options: const FirebaseOptions(
        apiKey: 'AIzaSyAXoqkEF858lnJvjVHZAOZG2L_WHImf_qs',
        appId: '1:410320759113:android:dd1b610555fdc75ead9298',
        messagingSenderId: '410320759113',
        projectId: 'assurapp-a7675',
        storageBucket: 'assurapp-a7675.appspot.com')
      )
      : await Firebase.initializeApp(
      options: const FirebaseOptions(
          apiKey: 'AIzaSyCQv6k7UYCU8uV8SzEZ-MK3t5JIYXrpEsY',
          appId: '1:410320759113:ios:19f085ff362ffdd3ad9298',
          messagingSenderId: '410320759113',
          projectId: 'assurapp-a7675',
          storageBucket: 'assurapp-a7675.appspot.com')
  );
  runApp(const AssurApp());
}

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
        '/expertise-view': (context) => const ExpertiseShowPage(),
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