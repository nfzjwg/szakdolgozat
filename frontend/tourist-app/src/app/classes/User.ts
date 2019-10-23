export class User{
    constructor(username : string, password: string, role:string, email : string){
        this.username= username;
        this.password=password;
        this.email= email;
        this.role = role;
    }
    id : number;
    username : string;
    password: string;
    email : string;
    role:string;
}