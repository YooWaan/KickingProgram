
terraform {
  required_providers {
    docker = {
	  source  = "kreuzwerker/Docker"
	  #source = "github.com/kreuzwerker/terraform-provider-docker"
      version = "2.12.2"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

resource "docker_image" "buzy" {
  name = "busybox:latest"
}
