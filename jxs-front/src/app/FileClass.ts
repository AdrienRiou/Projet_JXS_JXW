export class FileClass{
    name?: string
    id?: string
    size?: string
    lastEditDate?: string
    creationDate?: string
    authors?: string[]
    isFolder? : boolean
    service? : string
}

export class  FileListClass {
  files : FileClass[];
  constructor(files : FileClass[]
  ) {
    this.files = files
  }
  concatFiles(file:FileListClass):void{
    console.log("concatFiles")
    this.files.concat(file.files)
  }
}
