mod ex1;

fn main() {
    //let n = get_input().trim().parse::<i64>().unwrap();
    let n = ex1::count_up();
    println!("Hello, world!");
    println!("{}", n);
}
