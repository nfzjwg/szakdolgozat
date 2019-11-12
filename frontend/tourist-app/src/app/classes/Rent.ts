import { Car } from './Car';
import { Motobike } from './Motobike';
import { User } from './User';

export class Rent{

    constructor(start : Date, end : Date, ){
        this.start = start;
        this.end = end;
        }
    id : number;
    start : Date;
    end : Date;
    user : User;
    car : Car;
    motobike : Motobike;
    payed : boolean
}