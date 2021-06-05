
locals {
  foo = [1,2,3]
}

data "template_file"

output "output_local" {
  value = local.foo
}
