export class Rent{

    constructor(start : Date, end : Date, ){
        this.start = start;
        this.end = end;
        }
    id : number;
    start : Date;
    end : Date;
    userID : number;
    carID : number;
    motobikeID : number;
    payed : boolean
}