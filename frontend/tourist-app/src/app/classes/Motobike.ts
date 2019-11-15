import { User } from './User';

export class Motobike{
    constructor(){}

    id : number;
    manufacturer : string;
    model : string;
    ccm : number;
    rented : boolean;
    owner : User
}