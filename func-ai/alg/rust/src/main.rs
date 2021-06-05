//mod ex1;

extern crate clap;
use clap::{Arg, App, SubCommand};
//use clap::{Arg, App, SubCommand};



fn main() {
    let matches = App::new("My Super Program")
        .version("1.0")
        .author("Kevin K. <kbknapp@gmail.com>")
        .about("Does awesome things")

		.args_from_usage(
            "-c, --config=[FILE] 'Sets a custom config file'
                              <INPUT>              'Sets the input file to use'
                              -v...                'Sets the level of verbosity'")

        .subcommand(SubCommand::with_name("test")
                    .about("controls testing features")
                    .version("1.3")
                    .author("Someone E. <someone_else@other.com>")
                    .arg_from_usage("-d, --debug 'Print debug information'"))
        .get_matches();


    let config = matches.value_of("config").unwrap_or("default.conf");
    println!("Value for config: {}", config);

    //println!("Using input file: {}", matches.value_of("INPUT").unwrap());


    if let Some(matches) = matches.subcommand_matches("test") {
        if matches.is_present("debug") {
            println!("Printing debug info...");
        } else {
            println!("Printing normally...");
        }
    }
	
    //let n = get_input().trim().parse::<i64>().unwrap();
	/*
    let n = ex1::count_up();
    println!("Hello, world!");
    println!("{}", n);
	*/
}
