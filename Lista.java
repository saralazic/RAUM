package prm;


public class Lista{
	
	public Elem glava, rep, rd;
	
	public void rst() {
		glava=rep=rd=null;
	}
	
	public void printList() {
		System.out.print("\npostfix: ");
		for(Elem e=glava; e!=null; e=e.sledeci)
			System.out.print(" "+e.sadrzaj);
	}
	
	public void PUSH(Elem el)
	{
		Elem e=new Elem(el);
		e.sledeci=glava;
		glava=e;
	
	}
	
	public Elem POP() {
		Elem e=null;
		if(glava!=null)
		{
			e=glava;
			glava=glava.sledeci;
		}
		return e;
	}
	
	public Elem TOP() {
		return glava;
	}
	
	public boolean STACK_EMPTY() {
		if (glava==null)
			return true;
		return false;
	}
	
	public Elem INPUT() {
		Elem tmp=rd;
		if (rd!=null) rd=rd.sledeci;
		return tmp;
	}
	
	public void OUTPUT(Elem e) {
		if (glava==null) glava=rd=rep=new Elem(e);
		else {
			rep.sledeci=new Elem(e);
			rep=rep.sledeci;
		}
	}
	
	public boolean END() {
		if (rd==null) return true;
		return false;
	}
}
