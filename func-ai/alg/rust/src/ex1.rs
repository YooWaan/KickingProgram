use std::io;

fn get_input() -> i64 {
    let mut buffer = String::new();
    io::stdin().read_line(&mut buffer).ok();
    //.expect("Failed");
    buffer.trim().parse::<i64>().unwrap()
}


pub fn count_up() -> i64 {
    let n = get_input();
    println!("{}", n);

    let mut counter = 0;
    for i in 0..n {
        counter += i;
    }

    return counter;
}
