// voice-generator.component.ts

import { Component } from '@angular/core';
import { VoiceService } from '../voice.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-voice-generator',
  templateUrl: './voice-generator.component.html',
  styleUrls: ['./voice-generator.component.scss']
})
export class VoiceGeneratorComponent {
  file: string = '';
  voiceUrl: string = '';

  data: any;

  constructor(private voiceService: VoiceService,private http: HttpClient) {}

  // generateVoice() {
  //   if (this.text) {
  //     this.voiceService.generateVoice(this.text).subscribe((url) => {
  //       this.voiceUrl = url;
  //     });
  //   }
  // }

  responseString: string = '';

  generateVoice() {
    const textToInclude = this.file; 
    this.voiceService.getData(textToInclude).subscribe((response: any) => {
      // Assign the response string to your variable
      this.responseString = response.message;
    });
  }
  excelFile: File | null = null;


  
  onFileSelected(event: any) {
    this.excelFile = event.target.files[0];
  }

  uploadExcel() {
    if (this.excelFile) {
      const formData = new FormData();
      formData.append('file', this.excelFile);

      this.http.post<any>('http://localhost:8080/upload-excel', formData).subscribe(
        (response) => {
          this.responseString = response;
        },
        (error) => {
          console.error('Error:', error);
        }
      );
    }
  }
}
