import { Injectable } from '@angular/core';
import {User} from "../interfaces/user";
import {Insurance} from "../interfaces/insurance";
import {Payment} from "../interfaces/payment";
import * as pdfMake from 'pdfmake/build/pdfmake';
import { TDocumentDefinitions } from 'pdfmake/interfaces';


@Injectable({
  providedIn: 'root'
})
export class InvoiceService {

  constructor() {}

  download(client: User, insurance: Insurance, payment: Payment, start: Date, end: Date) {
    const pdf = this.generate(client, insurance, payment, start, end);
    pdf.download(`assurapp-invoice-0000${payment.id}-${insurance.insurer.name}-${insurance.insurer.lastname}`);
  }

  print(client: User, insurance: Insurance, payment: Payment, start: Date, end: Date) {
    const pdf = this.generate(client, insurance, payment, start, end);
    pdf.print();
  }

  private generate(client: User, insurance: Insurance, payment: Payment, start: Date, end: Date): pdfMake.TCreatedPdf {

    const invoice: TDocumentDefinitions = {
      content: [
        {
          columns: [
            {
              image:
                'data:image/png;base64,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',
              width: 150,
            },
            [
              {
                text: 'Receipt',
                color: '#333333',
                fontSize: 28,
                bold: true,
                alignment: 'right',
                margin: [0, 0, 0, 15],
              },
              {
                stack: [
                  {
                    columns: [
                      {
                        text: 'Receipt No.',
                        color: '#aaaaab',
                        bold: true,
                        width: '*',
                        fontSize: 12,
                        alignment: 'right',
                      },
                      {
                        text: `0000${payment.id}`,
                        bold: true,
                        color: '#333333',
                        fontSize: 12,
                        alignment: 'right',
                        width: 100,
                      },
                    ],
                  },
                  {
                    columns: [
                      {
                        text: 'Date Issued',
                        color: '#aaaaab',
                        bold: true,
                        width: '*',
                        fontSize: 12,
                        alignment: 'right',
                      },
                      {
                        text: `${payment.transactionDate}`,
                        bold: true,
                        color: '#333333',
                        fontSize: 12,
                        alignment: 'right',
                        width: 100,
                      },
                    ],
                  },
                  {
                    columns: [
                      {
                        text: 'Status',
                        color: '#aaaaab',
                        bold: true,
                        fontSize: 12,
                        alignment: 'right',
                        width: '*',
                      },
                      {
                        text: 'PAID',
                        bold: true,
                        fontSize: 14,
                        alignment: 'right',
                        color: 'green',
                        width: 100,
                      },
                    ],
                  },
                ],
              },
            ],
          ],
        },
        {
          columns: [
            {
              text: 'From',
              color: '#aaaaab',
              bold: true,
              fontSize: 14,
              alignment: 'left',
              margin: [0, 20, 0, 5],
            },
            {
              text: 'To',
              color: '#aaaaab',
              bold: true,
              fontSize: 14,
              alignment: 'left',
              margin: [0, 20, 0, 5],
            },
          ],
        },
        {
          columns: [
            {
              text: `${insurance.insurer.name} \n ${insurance.insurer.lastname}`,
              bold: true,
              color: '#333333',
              alignment: 'left',
            },
            {
              text: `${client.name} \n ${client.lastname.toUpperCase()}`,
              bold: true,
              color: '#333333',
              alignment: 'left',
            },
          ],
        },
        {
          columns: [
            {
              text: 'Address',
              color: '#aaaaab',
              bold: true,
              margin: [0, 7, 0, 3],
            },
            {
              text: 'Address',
              color: '#aaaaab',
              bold: true,
              margin: [0, 7, 0, 3],
            },
          ],
        },
        {
          columns: [
            {
              text: `${insurance.insurer.address?.split('%')[0]}\n${insurance.insurer.address?.split('%')[1]}\n${insurance.insurer.address?.split('%')[2]}`,
              style: 'invoiceBillingAddress',
            },
            {
              text: `${client.address?.split('%')[0]}\n${client.address?.split('%')[1]}\n${client.address?.split('%')[2]}`,
              style: 'invoiceBillingAddress',
            },
          ],
        },
        '\n\n',
        {
          //width: '100%,
          alignment: 'center',
          text: `Invoice No. 0000${payment.id}`,
          bold: true,
          margin: [0, 10, 0, 10],
          fontSize: 15,
        },
        {
          layout: {
            defaultBorder: false,
            hLineWidth: function(i, node) {
              return 1;
            },
            vLineWidth: function(i, node) {
              return 1;
            },
            hLineColor: function(i, node) {
              if (i === 1 || i === 0) {
                return '#bfdde8';
              }
              return '#eaeaea';
            },
            vLineColor: function(i, node) {
              return '#eaeaea';
            },
            hLineStyle: function(i, node) {
              // if (i === 0 || i === node.table.body.length) {
              return null;
              //}
            },
            // vLineStyle: function (i, node) { return {dash: { length: 10, space: 4 }}; },
            paddingLeft: function(i, node) {
              return 10;
            },
            paddingRight: function(i, node) {
              return 10;
            },
            paddingTop: function(i, node) {
              return 2;
            },
            paddingBottom: function(i, node) {
              return 2;
            },
            fillColor: function(rowIndex, node, columnIndex) {
              return '#fff';
            },
          },
          table: {
            headerRows: 1,
            widths: ['*', 80],
            body: [
              [
                {
                  text: 'Insurance',
                  fillColor: '#eaf2f5',
                  border: [false, true, false, true],
                  margin: [0, 5, 0, 5],
                  textTransform: 'uppercase',
                },
                {
                  text: 'Amount',
                  border: [false, true, false, true],
                  alignment: 'right',
                  fillColor: '#eaf2f5',
                  margin: [0, 5, 0, 5],
                  textTransform: 'uppercase',
                },
              ],
              [
                {
                  text: `${insurance.name}\t(${start} to ${end})`,
                  border: [false, false, false, true],
                  margin: [0, 5, 0, 5],
                  alignment: 'left',
                },
                {
                  border: [false, false, false, true],
                  text: `${payment.amount.toFixed(2)}`,
                  fillColor: '#f5f5f5',
                  alignment: 'right',
                  margin: [0, 5, 0, 5],
                },
              ],
              [
                {
                  text: `${insurance.terms[0].name}\t${insurance.terms[0].description}`,
                  border: [false, false, false, true],
                  margin: [75, 5, 0, 5],
                  alignment: 'left',
                },
                {

                }
              ],
              [
                {
                  text: `${insurance.terms[1].name}\t${insurance.terms[1].description}`,
                  border: [false, false, false, true],
                  margin: [75, 5, 0, 5],
                  alignment: 'left',
                },
                {

                }
              ],
              [
                {
                  text: `${insurance.terms[2].name}\t${insurance.terms[2].description}`,
                  border: [false, false, false, true],
                  margin: [75, 5, 0, 5],
                  alignment: 'left',
                },
                {

                }
              ],
            ],
          },
        },
        '\n',
        '\n\n',
        {
          layout: {
            defaultBorder: false,
            hLineWidth: function(i, node) {
              return 1;
            },
            vLineWidth: function(i, node) {
              return 1;
            },
            hLineColor: function(i, node) {
              return '#eaeaea';
            },
            vLineColor: function(i, node) {
              return '#eaeaea';
            },
            hLineStyle: function(i, node) {
              // if (i === 0 || i === node.table.body.length) {
              return null;
              //}
            },
            // vLineStyle: function (i, node) { return {dash: { length: 10, space: 4 }}; },
            paddingLeft: function(i, node) {
              return 10;
            },
            paddingRight: function(i, node) {
              return 10;
            },
            paddingTop: function(i, node) {
              return 3;
            },
            paddingBottom: function(i, node) {
              return 3;
            },
            fillColor: function(rowIndex, node, columnIndex) {
              return '#fff';
            },
          },
          table: {
            headerRows: 1,
            widths: ['*', 'auto'],
            body: [
              [
                {
                  text: 'Payment Subtotal',
                  border: [false, true, false, true],
                  alignment: 'right',
                  margin: [0, 5, 0, 5],
                },
                {
                  border: [false, true, false, true],
                  text: `${payment.amount.toFixed(2)}`,
                  alignment: 'right',
                  fillColor: '#f5f5f5',
                  margin: [0, 5, 0, 5],
                },
              ],
              [
                {
                  text: 'Total Amount',
                  bold: true,
                  fontSize: 20,
                  alignment: 'right',
                  border: [false, false, false, true],
                  margin: [0, 5, 0, 5],
                },
                {
                  text: `EUR ${payment.amount.toFixed(2)}`,
                  bold: true,
                  fontSize: 20,
                  alignment: 'right',
                  border: [false, false, false, true],
                  fillColor: '#f5f5f5',
                  margin: [0, 5, 0, 5],
                },
              ],
            ],
          },
        },
        '\n\n',
      ],
      styles: {
        notesTitle: {
          fontSize: 10,
          bold: true,
          margin: [0, 50, 0, 3],
        },
        notesText: {
          fontSize: 10,
        },
      },
      defaultStyle: {
        columnGap: 20,
      },
    };

    return pdfMake.createPdf(invoice, {}, {
      Roboto: {
        normal: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Regular.ttf',
        bold: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Medium.ttf',
        italics: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Italic.ttf',
        bolditalics: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-MediumItalic.ttf'
      }
    });

  }




}
