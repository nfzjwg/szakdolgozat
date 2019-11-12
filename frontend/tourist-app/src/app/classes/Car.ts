import { User } from './User';

export class Car{

    constructor(manufacturer : string, model : string){
        this.manufacturer = manufacturer;
        this.model = model;
    }
    id : number;
    manufacturer : string;
    model : string;
    doors: number;
    engine : string;
    ccm : number;
    ac : boolean;
    rented : boolean;
    owner: User;
}