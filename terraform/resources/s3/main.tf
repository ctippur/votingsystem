resource "aws_s3_bucket_object" "object" {
  bucket = "${var.bucket_name}"
  key    = "index.html"
  source = "../../../html/index.html"
  content_type = "text/html"
  etag = "${md5(file("../../../html/index.html"))}"
}
#if_thumbs_down_83035.png  if_thumbs_up_72054.png
resource "aws_s3_bucket_object" "thumbs_down" {
  bucket = "${var.bucket_name}"
  key    = "if_thumbs_down_83035.png"
  source = "../../../html/if_thumbs_down_83035.png"
  etag = "${md5(file("../../../html/if_thumbs_down_83035.png"))}"
}
resource "aws_s3_bucket_object" "thumbs_up" {
  bucket = "${var.bucket_name}"
  key    = "if_thumbs_up_72054.png"
  source = "../../../html/if_thumbs_up_72054.png"
  etag = "${md5(file("../../../html/if_thumbs_up_72054.png"))}"
}
