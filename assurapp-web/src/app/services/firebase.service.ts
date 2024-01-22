import { Injectable } from '@angular/core';
import { initializeApp } from "firebase/app";
import { getStorage, ref, uploadBytesResumable, getDownloadURL } from "firebase/storage";

@Injectable({
  providedIn: 'root'
})
export class FirebaseService {

  constructor() { }



  // Initialize Firebase
  firebaseConfig = {
    apiKey: "AIzaSyDVkEsylaTUeMkw8pL878SKUBmJyuRTDm8",
    authDomain: "assurapp-a7675.firebaseapp.com",
    projectId: "assurapp-a7675",
    storageBucket: "assurapp-a7675.appspot.com",
    messagingSenderId: "410320759113",
    appId: "1:410320759113:web:b3d99bc3ef9430d8ad9298"
  };

  firebaseApp = initializeApp(this.firebaseConfig);

  storage = getStorage(this.firebaseApp);

  uploadFile(file: File, path: string): Promise<string> {
    return new Promise((resolve, reject) => {
      const storageRef = ref(this.storage, path);
      const uploadTask = uploadBytesResumable(storageRef, file);

      uploadTask.on(
        "state_changed",
        (snapshot) => {
          const progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
        },
        (error) => {
          reject(error);
        },
        () => {
          getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
            resolve(downloadURL);
          });
        }
      );
    });
  }

}
