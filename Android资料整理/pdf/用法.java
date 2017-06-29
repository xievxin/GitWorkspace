PDDocument doc = PDDocument.load("D:/331.pdf");

PDFTextStripper stripper = new PDFTextStripper();

String text = stripper.getText(doc);

String title = stripper.getTitle(doc);

这是从本地读取pdf文件，如果是从网络上，你首先会得到文件的一个InputStream对象（假设名为stream），代码如下：

PDDocument doc = new PDDocument();

PDFParser parser = new PDFParser(stream);

parser.parse();

doc = parser.getPDDocument();

PDFTextStripper stripper = new PDFTextStripper();

String text = stripper.getText(doc);

String title = stripper.getTitle(doc);