use std::io::{self};


fn get_input() -> i64 {
    let mut buffer = String::new();
    io::stdin().read_line(&mut buffer).ok();
    //.expect("Failed");
    buffer.trim().parse::<i64>().unwrap()
}


fn main() {
    //let n = get_input().trim().parse::<i64>().unwrap();
    let n = get_input();
    println!("Hello, world!");
    println!("{}", n);
}
