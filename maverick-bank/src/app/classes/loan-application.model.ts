export class LoanApplication {
    loanId: number = 0;
    accountNo: number = 0;
    loanAmount: number = 0;
    tenureInMonths: number = 0;
    purpose: string = '';
    status: string = 'PENDING';
    appliedDate: Date = new Date();
}