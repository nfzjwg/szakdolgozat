import { User } from './User';
import { Car } from './Car';
import { Motobike } from './Motobike';

export class Receipt{

    constructor(){}
    id : number;
    start : Date;
    end : Date;
    cost : number;
    user : User;
    car : Car;
    motobike : Motobike;
    
}