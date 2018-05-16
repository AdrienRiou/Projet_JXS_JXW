export class FileClass{
    name: string
    id: string
    size: string
    lastEditDate: string
    creationDate: string
    authors: string[]
}

export interface  FileListClass {
  files : FileClass[]
}
