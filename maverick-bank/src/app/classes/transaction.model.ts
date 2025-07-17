export class Transaction {
    transactionId: number = 0;
    accountNo: number = 0;
    type: string = '';
    amount: number = 0;
    description: string = '';
    timestamp: Date = new Date();
}