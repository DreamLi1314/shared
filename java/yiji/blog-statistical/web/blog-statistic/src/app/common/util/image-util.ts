
export namespace ImageUtil {
   // @ts-ignore
   export function dataURLtoBlob(dataurl) {
      let arr = dataurl.split(',');
      let mime = arr[0].match(/:(.*?);/)[1];
      let bstr = window.atob(arr[1]);
      let n = bstr.length;
      let u8arr = new Uint8Array(n);

      while (n--) {
         u8arr[n] = bstr.charCodeAt(n);
      }

      return new Blob([u8arr], { type: mime });
   }

   export function downloadFile(url: string, name='图片'){
      var a = document.createElement("a");
      a.setAttribute("href",url);
      a.setAttribute("download", name);
      a.setAttribute("target","_blank");
      let clickEvent = document.createEvent("MouseEvents");
      clickEvent.initEvent("click", true, true);
      a.dispatchEvent(clickEvent);
   }

   export function downloadFileByBase64(base64: string, name='图片') {
      var myBlob = dataURLtoBlob(base64);
      var myUrl = URL.createObjectURL(myBlob);
      downloadFile(myUrl,name)
   }

   // @ts-ignore
   export const covertSVG2Image = (node, name = '图片', width = 400, height = 400, type = 'png') => {

      let serializer = new XMLSerializer();

      let source = '<?xml version="1.0" standalone="no"?>\r\n' + serializer.serializeToString(node)

      let image = new Image();

      image.src = 'data:image/svg+xml;charset=utf-8,' + encodeURIComponent(source)

      let canvas = document.createElement('canvas')

      canvas.width = width

      canvas.height = height

      let context = canvas.getContext('2d')

      // @ts-ignore
      context.fillStyle = '#fff'
      // @ts-ignore
      context.fillRect(0, 0, 10000, 10000)

      image.onload = function () {

         // @ts-ignore
         context.drawImage(image, 0, 0)

         let a = document.createElement('a')

         a.download = `${name}.${type}`

         a.href = canvas.toDataURL(`image/${type}`)

         a.click()

      }
   }
}
