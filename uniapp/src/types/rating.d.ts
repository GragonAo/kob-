
export type RatingInfo ={
    username:string,
    photo:string,
    rating:string
}

export type RatingList = {
forEach(rating: any, arg1: (RatingInfo: any) => void): unknown
    ratingInfo:RatingInfo[]
}

