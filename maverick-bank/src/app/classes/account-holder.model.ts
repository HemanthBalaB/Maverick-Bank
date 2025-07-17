export class AccountHolder {
  accountNo: number = 0;
  name!: string;
  email!: string;
  mobileNo!: string;
  password!: string;
  balance!: number;
  accountType!: string;
  address!: string;
  branchId!: number;    // ✅ Must be sent to backend to link with branch
  ifscCode!: string;    // ✅ Displayed from selected branch
}
