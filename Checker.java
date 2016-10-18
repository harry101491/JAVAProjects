/*
In this Code I have made classes of Super Block, RootDirINode, Directory, TXTFileInode,TxtFile to create the structure
that they have between them. For finding error in them I divided each class into separate unit for finding error. There are 
function which find time error pointer error and size error. I not able to provide write functionality in time for some errors
but I have provided write functionality for free pointers and other.
*/
package fileChecker;
import java.io.*;
import java.util.*;
class TXTFile // class TXTFile which only contain a file of data
{
	private File DataFile; // object variable data file
	public File getDataFile() //getter function
	{
		return DataFile;
	}
	public void setDataFile(File dataFile) //setter function 
	{
		DataFile = dataFile;
	}	
}
class RootDirINode //class of RootDirectory INode 
{
	public static String directoryName = "/"; // the name of directory is fixed 
	private int blockNumber; // block of Root Directory Node
	private double mode; // mode of root directory
	private int countFile = 0;
	private int countDir = 0;
	private double size; // size of directory
	private int error = 0; //checking whether error has occured or not
	private double gid; // gid of directory
	private double uid;	 // uid of directory
	private double atime; // access time of root directory 
	private double ctime; // creation time of root directory 
	private double mtime; // modification time of root dirctory
	private int linkCount; // link count of the root directory
	private Directory rootDir[] = null; // array of DirectoryInode which it points
	private TXTFileINode txtfile[] = null; // array of TxtFileInode which it points
	public RootDirINode(int block) // constructer for the RootDirctory
	{
		char buffer[] = new char[4096];
		this.blockNumber = block; // setting the block Number that we got from the super Block
		int countFile = 0; // count the number of files in root directory
		int countDir = 0; // count the number of directory in this root directory
		int count = 0; // count the number of times loops run
		File f = new File(SuperBlock.path+"fusedata."+blockNumber); // getting the file for the Root Directory
		HashMap<String,Float> h = new HashMap<String, Float>(); // hash map to find the values and put them in object variable
		try
		{
			FileReader fr = new FileReader(f);  // setting file Reader for this file
			fr.read(buffer); // reading the buffer and doing some parsing for getting it into hash map
			StringBuilder str = new StringBuilder(); // string builder to get the string from the file
			str.append(buffer); // appending buffer to the string builder
			String s = str.toString().trim().replace("{","").replace("}",""); //replacing curly braces and removing white spaces
			String st[] = s.split(","); // splitting on comma
			for(int i=0;i<st.length;i++) // again removing the white spaces
			{
				st[i] = st[i].trim();
			}
			for(int i=0;i<st.length;i++) // parsing the data from string to the hash map
			{
				if(i<8)
				{
					String stri[] = st[i].split(":");  // splitting the string by :
					for(int p=0;p<=(stri.length)/2;p=p+2) // parsing the string and storing it in the hash map
					{
						h.put(stri[p],Float.valueOf(stri[p+1]));
					}
				}
				else
				{
					count++;
					int linkCount = Math.round(h.get("linkcount")); // according to link count we can assume that the dir pointer and file pointer should be less than link count
					this.rootDir = new Directory[linkCount]; // setting the directory pointers 
					this.txtfile = new TXTFileINode[linkCount]; // setting the file pointers
					if(i==8)
					{
						//System.out.println("the value of i is:"+i);
						String st1 = st[i].replace("filename_to_inode_dict:","");  // to remove filename_to_inode_dict
						String ms = st1.trim();
						if(ms.charAt(0) == 'f')
						{
							//System.out.println("inside f"+countFile);
							String fileName = ms.substring(2,(ms.length()-3));  // setting the file name 
							int inodeBlock = Integer.valueOf((String)ms.subSequence((ms.length()-2),ms.length())); // setting File Inode Block Number
							this.txtfile[countFile] = new TXTFileINode(fileName,inodeBlock,blockNumber);  //TxtFileInode is object is Made
							countFile++; // increases the countFile
						}
						else
						{
							if(st[i].charAt(0) == 'd' || st[i].contains(".") || st[i].contains("..")) // condition for checking whether it is d or . or ..
							{
								if(st[i].contains(".") && !st[i].contains(".."))
								{
									String str1 = st[i].substring((st[i].length()-2),st[i].length());
									if(Integer.valueOf(str1) == blockNumber)
									{
										//System.out.println("In this "+RootDirINode.directoryName+" self pointing block is correct"); //checking whether the . is self pointing
									}
									else
									{
										System.out.println("the self pointing "+RootDirINode.directoryName+" block is not correct"); // if does not have a self pointing pointer
									}
								}
								if(st[i].contains(".."))
								{
									String str1 = st[i].substring((st[i].length()-2),st[i].length());
									if(Integer.valueOf(str1) == blockNumber)
									{
										//System.out.println("In this "+RootDirINode.directoryName+" the parent block is correct"); // checking whether the .. is parent pointing
									}
									else
									{
										System.out.println("In this directory "+RootDirINode.directoryName+" the parent block is not correct");
									}
								}
								if(st[i].charAt(0) == 'd' && !st[i].contains(".") && !st[i].contains("..")) // checking for d directory and name
								{
									String dirName = st[i].substring(2,(st[i].length()-3));  // setting the dir name 
									int inodeBlock = Integer.valueOf((String)st[i].subSequence((st[i].length()-2),st[i].length())); // block for directory
									this.rootDir[countDir] = new Directory(dirName,inodeBlock,this.blockNumber);
									countDir++;
								}
							}
						}
					}
					else
					{
						//System.out.println("the value of i is:"+i);
						if(st[i].charAt(0) == 'd' || st[i].contains(".") || st[i].contains("..")) // condition for checking whether it is d or . or ..
						{
							if(st[i].contains(".") && !st[i].contains(".."))
							{
								String str1 = st[i].substring((st[i].length()-2),st[i].length());
								if(Integer.valueOf(str1) == blockNumber)
								{
									//System.out.println("In this "+RootDirINode.directoryName+" self pointing block is correct"); //checking whether the . is self pointing
								}
								else
								{
									System.out.println("the self pointing in directory "+RootDirINode.directoryName+" block is not correct"); // if does not have a self pointing pointer
								}
							}
							if(st[i].contains(".."))
							{
								String str1 = st[i].substring((st[i].length()-2),st[i].length());
								if(Integer.valueOf(str1) == blockNumber)
								{
									//System.out.println("In this "+RootDirINode.directoryName+" the parent block is correct"); // checking whether the .. is parent pointing
								}
								else
								{
									System.out.println("In the directory "+RootDirINode.directoryName+" the parent block is not correct");
								}
							}
						}
						if(st[i].charAt(0) == 'd' && !st[i].contains(".") && !st[i].contains("..")) // checking for d directory and name
						{
							String dirName = st[i].substring(2,(st[i].length()-3));  // setting the dir name 
							int inodeBlock = Integer.valueOf((String)st[i].subSequence((st[i].length()-2),st[i].length())); // block for directory
							this.rootDir[countDir] = new Directory(dirName,inodeBlock,this.blockNumber);
							countDir++;
						}
					}
				}
			}
			this.size = h.get("size");
			this.linkCount = Math.round(h.get("linkcount"));
			this.uid = h.get("uid");
			this.gid = h.get("gid");
			this.ctime = h.get("ctime");
			this.atime = h.get("atime");
			this.mode = h.get("mode");
			this.mtime = h.get("mtime");
			this.countFile = countFile;
			this.countDir = countDir;
			if(this.linkCount != count)
			{
				System.out.println("The number of pointers in file_to_inode_dict is not correct"); // checking whether number of pointer in file to dict is correct
			}
			this.findCorrectTime();
			fr.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	// this functionality is not provided
	/*public void writingAfterError()
	{
		this.findCorrectTime();
		if(this.error == 1)
		{
			try
			{
				PrintWriter out = new PrintWriter(SuperBlock.path+"fusedata."+this.blockNumber);
				String s1 = "";
				String s = "{size:"+this.size+", uid:"+this.uid+", gid:"+this.gid+", mode:"+this.mode+", atime:"+this.atime+", ctime:"+this.ctime+", mtime:"+this.mtime+", linkcount:"+this.linkCount+" ,filename_to_inode_dict:{";
				for(int i=0;i<this.countFile;i++)
				{
					s1 = s1+"f:"+this.txtfile[i].getName()+":"+this.txtfile[i].getBlockNumber()+", ";
				}
				String s2 = "d:.:"+this.blockNumber+", ";
				String s3 = "d:..:"+this.blockNumber+", ";
				String s4 = "";
				for(int m=0;m<this.countDir;m++)
				{
					if(m == this.countDir-1)
					 {
						s4 = s4+"d:"+this.rootDir[m].getDirectoryName()+":"+this.rootDir[m].getBlockNumber()+"}}";
					 }
					else
					{
					    s4 = s4+"d:"+this.rootDir[m].getDirectoryName()+":"+this.rootDir[m].getBlockNumber()+", ";
					}
				}
				String str = s+s1+s2+s3+s4;
				out.print(str);
				out.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}*/
	public void findCorrectTime()
	{
		double creationTime = this.ctime; // creation Time of File Inode
		double modificationTime = this.mtime; // modificationTime of File INode
		double accessTime = this.atime; // access time of File INode
		double currentTime = (System.currentTimeMillis()/1000L); // System currentTime
		if(creationTime > currentTime) // if Time is in future
		{
			System.out.println("Time error in creation Time of directory "+RootDirINode.directoryName); // Time error 
			this.ctime = currentTime;
		}
		else if(modificationTime > currentTime)
		{
			System.out.println("Time error in modification Time of directory "+RootDirINode.directoryName); // Time error
			this.mtime = currentTime;
		}
		else if(accessTime > currentTime)
		{
			System.out.println("Time error in accessTime Time of directory "+RootDirINode.directoryName); // Time error
			this.atime = currentTime;
		}
		else
		{
			//time is correct
		}
	}
}
class Directory // class to store the information about the Directory
{
	private int blockNumber; // block number for directory
	private String directoryName; // name of directory
	private int parentBlock; // parent Block
	private double mode; // mode
	private double size; // size
	private double gid; // gid 
	private double uid; // uid
	private double atime; // accesstime
	private double ctime; // creation time
	private double mtime; // modification time
	private int linkCount; // link count
	private Directory dir[] = null; // directory array
	private TXTFileINode txtFile[] = null; // txtFile Inode array
	public Directory(String Name,int block,int parentBlock) // Setting the Default value of Directory Object
	{
		this.directoryName = Name; // setting the directory Name
		this.blockNumber = block; // setting the directory Block
		this.parentBlock = parentBlock; // setting the parent Block
		char buffer[] = new char[SuperBlock.maxByte];  // allocating the buffer
		HashMap<String,Float> h = new HashMap<String, Float>(); // hash map for parsing data
		int countFile = 0; // count the number of files in directory
		int countDir = 0; // count the number of directory in this directory
		int count = 0; // checking that how many times loop is running
		File f = new File(SuperBlock.path+"fusedata."+this.blockNumber); // getting File ready
		try  // parsing the data
		{
			FileReader fr = new FileReader(f);
			fr.read(buffer);
			StringBuilder str = new StringBuilder();
			str.append(buffer);
			String s = str.toString().trim().replace("{","").replace("}","");
			String st[] = s.split(",");
			for(int i=0;i<st.length;i++)
			{
				st[i] = st[i].trim();
				//System.out.println(st[i]+" ");
			}
			for(int i=0;i<st.length;i++)
			{
				if(i<8)
				{
					String stri[] = st[i].split(":");
					for(int p=0;p<=(stri.length)/2;p=p+2)
					{
						h.put(stri[p],Float.valueOf(stri[p+1]));
					}
				}
				else
				{
					count++;
					int linkCount = Math.round(h.get("linkcount"));
					this.dir = new Directory[linkCount];
					this.txtFile = new TXTFileINode[linkCount];
					if(i==8)
					{
						String st1 = st[i].replace("filename_to_inode_dict:","");  // to remove filename_to_inode_dict
						String ms = st1.trim();
						if(ms.charAt(0) == 'f')
						{
							String fileName = ms.substring(2,(ms.length()-3));  // setting the file name 
							int inodeBlock = Integer.valueOf((String)ms.subSequence((ms.length()-2),ms.length())); // setting File Inode Block Number
							this.txtFile[countFile] = new TXTFileINode(fileName,inodeBlock,this.blockNumber);  //TxtFileInode is object is Made
							countFile++;
						}
						else if(st[i].charAt(0) == 'd' || st[i].contains(".") || st[i].contains("..")) // condition for checking whether it is d or . or ..
						{
							if(st[i].contains(".") && !st[i].contains(".."))
							{
								String str1 = st[i].substring((st[i].length()-2),st[i].length());
								if(Integer.valueOf(str1) == this.blockNumber)
								{
									//System.out.println("In this "+this.directoryName+" self pointing block is correct"); //checking whether the . is self pointing
								}
								else
								{
									System.out.println("the self pointing block in the directory "+this.directoryName+" is not correct"); // showing that there is an error
								}
							}
							if(st[i].contains(".."))
							{
								String str1 = st[i].substring((st[i].length()-2),st[i].length());
								if(Integer.valueOf(str1) == this.parentBlock )
								{
									//System.out.println("In this "+this.directoryName+" the parent block is correct"); // checking whether the .. is parent pointing
								}
								else
								{
									System.out.println("In the directory "+this.directoryName+" parent block is not correct"); // whether .. is correct or not
								}
							}
						}
					}
					else
					{
						if(st[i].charAt(0) == 'd' || st[i].contains(".") || st[i].contains("..")) // condition for checking whether it is d or . or ..
						{
							if(st[i].contains(".") && !st[i].contains(".."))
							{
								String str1 = st[i].substring((st[i].length()-2),st[i].length());
								if(Integer.valueOf(str1) == this.blockNumber)
								{
									//System.out.println("In this "+this.directoryName+" self pointing block is correct"); //checking whether the . is self pointing
								}
								else
								{
									System.out.println("the self pointing block in the directory "+this.directoryName+" is not correct");
								}
							}
							if(st[i].contains(".."))
							{
								String str1 = st[i].substring((st[i].length()-2),st[i].length());
								if(Integer.valueOf(str1) == this.parentBlock )
								{
									//System.out.println("In this "+this.directoryName+" the parent block is correct"); // checking whether the .. is parent pointing
								}
								else
								{
									System.out.println("In this "+this.directoryName+" the parent block is not correct");
								}
							}
						}
						if(st[i].charAt(0) == 'd' && !st[i].contains(".") && !st[i].contains("..")) // checking for d directory and name
						{
							String dirName = st[i].substring(2,(st[i].length()-3));  // setting the dir name 
							int inodeBlock = Integer.valueOf((String)st[i].subSequence((st[i].length()-2),st[i].length())); // current block that directory resides
							this.dir[countDir] = new Directory(dirName,inodeBlock,this.blockNumber); // setting the object variable 
							countDir++;
						}
					}
				}
			}
			this.size = h.get("size");
			this.mode = h.get("mode");
			this.gid = h.get("gid");
			this.atime = h.get("atime");
			this.ctime = h.get("ctime");
			this.mtime = h.get("mtime");
			this.uid = h.get("uid");
			this.linkCount = Math.round(h.get("linkcount"));
			if(this.linkCount != count)
			{
				this.linkCount = count;
				System.out.println("The number of pointers in file_to_inode_dict is not correct"); // checking whether number of pointer in file to dict is correct
			}
			this.findCorrectTime();
			fr.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void findCorrectTime()
	{
		double creationTime = this.ctime; // creation Time of File Inode
		double modificationTime = this.mtime; // modificationTime of File INode
		double accessTime = this.atime; // access time of File INode
		double currentTime = (System.currentTimeMillis()/1000L); // System currentTime
		if(creationTime > currentTime) // if Time is in future
		{
			System.out.println("Time error in creation Time of directory "+this.directoryName); // Time error 
			this.ctime = currentTime;
		}
		else if(modificationTime > currentTime)
		{
			System.out.println("Time error in modification Time of directory "+this.directoryName); // Time error
			this.mtime = currentTime;
		}
		else if(accessTime > currentTime)
		{
			System.out.println("Time error in accessTime Time of directory "+this.directoryName); // Time error
			this.atime = currentTime;
		}
		else
		{
			//time is correct
		}
	}
	public int getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}
	public String getDirectoryName() {
		return directoryName;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	public int getParentBlock() {
		return parentBlock;
	}
	public void setParentBlock(int parentBlock) {
		this.parentBlock = parentBlock;
	}
	public double getMode() {
		return mode;
	}
	public void setMode(double mode) {
		this.mode = mode;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public double getGid() {
		return gid;
	}
	public void setGid(double gid) {
		this.gid = gid;
	}
	public double getUid() {
		return uid;
	}
	public void setUid(double uid) {
		this.uid = uid;
	}
	public double getAtime() {
		return atime;
	}
	public void setAtime(double atime) {
		this.atime = atime;
	}
	public double getCtime() {
		return ctime;
	}
	public void setCtime(double ctime) {
		this.ctime = ctime;
	}
	public double getMtime() {
		return mtime;
	}
	public void setMtime(double mtime) {
		this.mtime = mtime;
	}
	public int getLinkCount() {
		return linkCount;
	}
	public void setLinkCount(int linkCount) {
		this.linkCount = linkCount;
	}
	public Directory[] getDir() {
		return dir;
	}
	public void setDir(Directory[] dir) {
		this.dir = dir;
	}
	public TXTFileINode[] getTxtFile() {
		return txtFile;
	}
	public void setTxtFile(TXTFileINode[] txtFile) {
		this.txtFile = txtFile;
	}
	
}
class TXTFileINode  // class for TXTFile INode
{
	private static int blockLength = 4096;  //block length is fixed
	private static String path = "G:\\JAVACode\\FileSystem\\src\\FS\\"; // path is fixed
	private String Name = null; // name of textfile
	private int blockNumber; // blockNumber in which TextInode resides
	private double size;  // size of txt file
	private double uid; // uid for txt file
	private double gid; // gid for txt file
	private double mode; // mode for txt file
	private int linkcount; // link count for text file
	private int parentDirectoryBlock; // parent directory of text file
	private double atime;  // atime for txt file
	private double ctime; // ctime for txt file
	private double mtime; // mtime for txt file
	private int indirect; // indirect for txt file
	private int location; // location for txt file
	private TXTFile file[] = null; // data for txt file as an array
	
	public TXTFileINode(String fileName, int block,int parentBlock) // Constructer for Inode which take fileName,its block Number and parentBlock as argument
	{
		this.parentDirectoryBlock = parentBlock;
		char buffer[] = new char[4096];
		this.blockNumber = block;
		this.Name = fileName;
		File f = new File(path+"fusedata."+block);
		try
		{
			FileReader fr = new FileReader(f);
			fr.read(buffer);
			StringBuilder str = new StringBuilder();
			str.append(buffer);
			String s = str.toString().trim().replace("{","").replace("}","");
			String st[] = s.split(",");
			for(int i=0;i<st.length;i++)
			{
				st[i] = st[i].trim();
			}
			HashMap<String,Float> h = new HashMap<String, Float>();
			for(int i=0;i<st.length;i++)
			{
				if(i==8)
				{
					String st1[] = st[i].split(" ");
					for(int m=0;m<st1.length;m++)
					{
						String str1[] = st1[m].split(":");
						for(int l=0;l<=(str1.length)/2;l=l+2)
						{
							h.put(str1[l],Float.valueOf(str1[l+1]));
						}
					}
				}
				else
				{
					String stri[] = st[i].split(":");
					for(int p=0;p<=(stri.length)/2;p=p+2)
					{
						h.put(stri[p],Float.valueOf(stri[p+1]));
					}
				}
			}
			this.linkcount = Math.round(h.get("linkcount"));
			this.uid = h.get("uid");
			this.size = h.get("size");
			this.gid = h.get("gid");
			this.ctime = h.get("ctime");
			this.atime = h.get("atime");
			this.mode = h.get("mode");
			this.mtime = h.get("mtime");
			this.indirect = Math.round(h.get("indirect"));
			this.location = Math.round(h.get("location"));
			this.file = null;
			this.findCorrectTimeFileInode();
			this.findSizeCorrect();
			fr.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void findCorrectTimeFileInode() // to find the time is correct or not in the fileInode
	{
		double creationTime = this.ctime; // creation Time of File Inode
		double modificationTime = this.mtime; // modificationTime of File INode
		double accessTime = this.atime; // access time of File INode
		double currentTime = (System.currentTimeMillis()/1000L); // System currentTime
		if(creationTime > currentTime) // if Time is in future
		{
			System.out.println("Time error in creation Time of FileINode in File "+this.Name); // Time error 
			this.ctime = currentTime;
		}
		else if(modificationTime > currentTime)
		{
			System.out.println("Time error in modification Time of FileINode in File"+this.Name); // Time error
			this.mtime = currentTime;
		}
		else if(accessTime > currentTime)
		{
			System.out.println("Time error in accessTime Time of FileINode in File "+this.Name); // Time error
			this.atime = currentTime;
		}
		else
		{
			//time is correct
		}
	}
	public void findSizeCorrect() // checks whether file size is a function of blockLenght or not
	{
		int size = (int)this.size;
		if(size < blockLength && size != 0)  // checking of condition a of 7 in specification
		{
			if(this.indirect != 0) // if indirect is non zero then there is an error
			{
				System.out.println("There is an error in "+this.Name+" indirect should be zero as size is less than block size");
				this.indirect = 0;
			}
			else // if location array length is one and indirect is zero
			{
				char buffer[] = new char[4096];
				File f = new File(path+"fusedata."+location);
				try
				{
					FileReader fr = new FileReader(f);
					fr.read(buffer);
					StringBuilder str = new StringBuilder();
					str.append(buffer);
					String st = str.toString().trim(); // finding the data File
					this.setFile(new TXTFile[st.length()]); // creating an array of data files
					for (int i=0;i<st.length();i++)
					{
						File f1 = new File(path+"fusedata."+Integer.valueOf(st));
					    this.getFile()[i].setDataFile(f1);  // setting an array of data files
					}
					fr.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		else if(size > blockLength) // if size is greater than block size
		{
			if(indirect != 0)
			{
				char buffer[] = new char[4096];
				File f = new File(path+"fusedata."+location);
				try
				{
					FileReader fr = new FileReader(f);
					fr.read(buffer);
					StringBuilder str = new StringBuilder();
					str.append(buffer);
					String st[] = str.toString().trim().split(","); // splitting of pointers
					this.setFile(new TXTFile[st.length]); // Making an array of Data file
					for(int i=0;i<st.length;i++)
					{
						File f1 = new File(path+"fusedata."+Integer.valueOf(st[i].trim())); // setting the TXT File if length of location is more
						this.getFile()[i].setDataFile(f1); // setting the dataFile
					}
					if(!(size > ((st.length-1)*blockLength))) // checking condition c of 7 in specification
					{
						System.out.println("there is an error size in File "+this.Name);
					}
					else if (!(size < (st.length*blockLength))) //checking condition b of 7 in specification
					{
						System.out.println("there is an error size in File "+this.Name);
					}
					else // if both condition are not satisfied then there is no problem
					{
						
					}
					fr.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	// setter and getter of object variables
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(int blockNumber) {
		this.blockNumber = blockNumber;
	}
	public int getParentDirectoryBlock() {
		return parentDirectoryBlock;
	}
	public void setParentDirectoryBlock(int parentDirectoryBlock) {
		this.parentDirectoryBlock = parentDirectoryBlock;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public double getUid() {
		return uid;
	}
	public void setUid(double uid) {
		this.uid = uid;
	}
	public double getGid() {
		return gid;
	}
	public void setGid(double gid) {
		this.gid = gid;
	}
	public double getMode() {
		return mode;
	}
	public void setMode(double mode) {
		this.mode = mode;
	}
	public int getLinkcount() {
		return linkcount;
	}
	public void setLinkcount(int linkcount) {
		this.linkcount = linkcount;
	}
	public double getAtime() {
		return atime;
	}
	public void setAtime(double atime) {
		this.atime = atime;
	}
	public double getCtime() {
		return ctime;
	}
	public void setCtime(double ctime) {
		this.ctime = ctime;
	}
	public double getMtime() {
		return mtime;
	}
	public void setMtime(double mtime) {
		this.mtime = mtime;
	}
	public int getIndirect() {
		return indirect;
	}
	public void setIndirect(int indirect) {
		this.indirect = indirect;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public TXTFile[] getFile() {
		return file;
	}
	public void setFile(TXTFile[] file) {
		this.file = file;
	}
}
class SuperBlock  // class for super Block
{
	public static String path = "G:\\JAVACode\\FileSystem\\src\\FS\\"; // path to find the file is fixed
	public static int blockNumber = 0; // super block Number
	public static int maxByte = 4096; // maximum size
	public static int FileSystemNumber = 20; // FileSystemNumber
	public static int maxPointers = 400; // maximum pointers that can reside in the file
	private double maxBlocks; // maximum number of blocks 10000 in this case
	private double creationTime; // creation Time for the Super Block
	private int mounted; // number of file system is mounted
	private int devId; // it is the type of file system
	private int freeStart; // starting Block of Free List
	private int freeEnd; // ending Block of Free List
	private RootDirINode rootBlock = null; // root Directory
	public SuperBlock()
	{
		
		char buffer[] = new char[4096]; // char buffer
		try
		{
			File f = new File(path+"fusedata."+SuperBlock.blockNumber); // setting of File variable
			FileReader fileReader = new FileReader(f); // setting file Reader
			HashMap<String,Float> hash = new HashMap<String,Float>(); // setting hash map
			fileReader.read(buffer); // reading the buffer
			StringBuilder str = new StringBuilder(); // string builder
			str.append(buffer);  // appending buffer
			String s = str.toString().trim().replace("}","").replace("{",""); // trimming and replacing { and }
			String[] st = s.split(","); // splitting on ,
			for (String s1: st)
			{
				String[] s2 = s1.trim().split(":");
				for(int i=0;i<=(s2.length)/2;i=i+2)
				{
					hash.put(s2[i],Float.valueOf(s2[i+1])); // putting values in hash map
				}
			}
			this.creationTime = hash.get("creationTime"); // setting the object variables
			this.devId = Math.round(hash.get("devId"));
			this.mounted = Math.round(hash.get("mounted"));
			this.freeStart = Math.round(hash.get("freeStart"));
			this.freeEnd = Math.round(hash.get("freeEnd"));
			this.maxBlocks = hash.get("maxBlocks");
			this.rootBlock = new RootDirINode(Math.round(hash.get("root")));
			this.findCorrectTime(); // calling the function to find time is correct or not
			this.findFileSystem(); // calling the function to find file system is correct or not
			this.findPointerCorrect(); // calling the function to find pointers are correct or not
			fileReader.close(); // closing the reader
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void findFileSystem() // finding file System is correct or not
	{
		if(this.devId != SuperBlock.FileSystemNumber)
		{
			System.out.println("File System is not correct");
			this.devId = SuperBlock.FileSystemNumber;
		}
	}
	public void findCorrectTime() // finding file pointers are correct or not.
	{
		float currentTime = System.currentTimeMillis()/1000L; // current Time Unix Epoch
		if (creationTime > currentTime)
		{
			System.out.println("There is Time Error in SuperBlock "); // if time is not correct
			this.creationTime = currentTime; // setting the current time
		}
	}
	public void findPointerCorrect()
	{
		int start,end;
		start = this.freeStart; // start File for the free Pointers
		end = this.freeEnd; // end File for the free Pointers
		if((maxBlocks/maxPointers) != (end-start)+1) // condition to check whether the size of free block list is related to maxBlock
		{
			System.out.println("Free pointer Files are not as function of free start and free end");
		}
		else
		{
			for(int i=start;i<=end;i++) // looping through the blocks to check the pointers
			{
				File f = new File(path+"fusedata."+i); // accessing the file
				char buffer[] = new char[4096]; // buffer for reading
				try
				{
					FileReader fr = new FileReader(f); // File Reader for that file
					fr.read(buffer); // reading from the file
					StringBuilder str = new StringBuilder(); // string builder to get the file data
					str.append(buffer);
					String s = str.toString().trim(); 
					//System.out.print(s+"\n");
					String[] st = s.split(",");  // splitting on the ,
					for(int m=0;m<st.length;m++)
					{
						st[m] = st[m].trim(); // trimming every string
					}
					if(st.length <= maxPointers)  // checking the length
					{
						//System.out.println("hi");
						for(int p=0;p<(st.length-1);p++)
						{
							//System.out.println("hi"+p);
							String file = st[p];
							File f1 = new File(path+"fusedata."+Integer.valueOf(file));
							if(f1.exists())
							{
								System.out.println("There is file existing for free pointer"); // checking the condition of b for 3 in specification
							}
							else if((Integer.valueOf(st[p+1]))-1 != Integer.valueOf(st[p])) //checking whether free pointers are correct or not
							{
								File f2 = new File(SuperBlock.path+"fusedata."+i); // file where error has occurred 
								PrintWriter out = new PrintWriter(f2); // PrintWriter to write to file
								StringBuilder builder = new StringBuilder(); // String builder to build the string
								int startingError = Integer.valueOf(st[p]); // starting of error free pointers
								int endError = Integer.valueOf(st[p+1]); //end of the error free pointers
								for(int m=0;m<=p;m++)
								{
								 builder.append(st[m]+", "); // appending the string before the error
								}
								for(int n=startingError+1;n<endError;n++)
								{
									builder.append(n+", "); // appending free pointers that are not in the file
								}
								for(int q=p+1;q<st.length;q++)
								{
									if(q == st.length-1)  // appending free pointer after the error
									{
										builder.append(st[q]);
									}
									else
									{
										builder.append(st[q]+", ");
									}
								}
								out.print(builder.toString());
								System.out.println("There is an error in free pointers:"+st[p]+" and "+st[p+1]); // checking the condition of a for 3 in specification
								out.close();
							}
							else if(Integer.valueOf(st[p]) > maxBlocks)
							{
								System.out.println("Number of Pointers are not correct"); // checking whether the pointer should not be greater than maxBlocks
							}
						}
					}
					else
					{
						System.out.println("There is an error in size of pointers in file"); // size in a file is more than 400 pointers
					}
					fr.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
public class Checker {
	public static void main(String[] args) {
		SuperBlock sup = new SuperBlock();
	}
}