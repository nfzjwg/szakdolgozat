import { Favourite } from './Favourite';
import { Rent } from './Rent';
import { Car } from './Car';
import { Motobike } from './Motobike';
import { Receipt } from './Receipt';

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
    ratingNumber : number;
    ratingValue : number;
    favourites : Favourite;
    rent : Rent;
    cars : Car;
    motobikes : Motobike;
    receipt : Receipt
}