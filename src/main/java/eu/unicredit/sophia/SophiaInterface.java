package eu.unicredit.sophia;

import java.nio.Buffer;
import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public class SophiaInterface {
	
		static
		{
			try
			{				
				System.loadLibrary("SophiaJava");
			}
			catch(UnsatisfiedLinkError ule)
			{
				System.out.println("Cannot load library");
				System.out.println(ule);
			}		
		}
		
		//memory utils
		public native MemoryArea allocate_mem(long capacity);
		public native MemoryArea get_mem(long addr, long capacity);
		public native long get_mem_capacity(Buffer buffer);
		public native long get_mem_address(Buffer buffer);
		public native void free_mem(long addr);
	
		
		//SP_API void *sp_env(void);
		public native long sp_env();
		//SP_API void *sp_ctl(void*, ...);
		//sp_ctl(env): get an environment control object.
		public native long sp_ctl(long... args);
		//SP_API void *sp_object(void*, ...);
		//sp_object(database): create new object for a transaction on selected database.
		//sp_object(cursor): get current positioned object.
		public native long sp_object(long... args);
		//SP_API int   sp_open(void*, ...);
		//sp_open(env): create environment, open or create pre-defined databases.
		//sp_open(database): create or open database.
		public native int sp_open(long... args);
		//SP_API int   sp_destroy(void*, ...);
		//sp_destroy(env): from examples to close everything
		public native int sp_destroy(long... args);
		//SP_API int   sp_error(void*, ...);
		//sp_error(env): check if there any error leads to the shutdown.
		public native int sp_error(long... args);
		//SP_API int   sp_set(void*, ...);
		//sp_set(ctl, "key", "value") Set configuration variable value or call a system procedure.
		//sp_set(database, object) Do a single-statement transaction.
		//sp_set(transaction, object) Do a key update as a part of multi-statement transaction.
		//sp_set(object, "field", ptr, size) Set or update an object field.
		public native int sp_set(long... args);
		//SP_API int   sp_delete(void*, ...);
		//sp_delete(database, object) Do a single-statement transaction.
		//sp_delete(transaction, object) Do a key deletion as a part of multi-statement transaction.
		public native int sp_delete(long... args);
		//SP_API void *sp_get(void*, ...);
		//sp_get(ctl, "key") Get configuration variable value object.
		//sp_get(database, object) Do a single-statement transaction.
		//sp_get(transaction, object) Do a key search as a part of multi-statement transaction visibility.
		//sp_get(object, "field", &size) Get an object field and its size. Size can be NULL.
		public native long sp_get(long... args);
		//SP_API int   sp_drop(void*, ...);
		//sp_drop(snapshot) Delete snapshot.
		//sp_drop(database) Close a database (v1.2.1 equal to sp_destroy(3)).
		public native int sp_drop(long... args);
		//SP_API int   sp_commit(void*, ...);
		//sp_cursor(ctl) Create cursor over all configuration values (single order only).
		//sp_cursor(database, object) Create a database cursor. Object might have position key and iteration order set. Supported orders: ">", ">=", "<, "<=".
		public native long sp_commit(long... args);
		//SP_API void *sp_begin(void*, ...);
		//sp_begin(env): create a transaction
		public native long sp_begin(long... args);
		//SP_API int   sp_prepare(void*, ...);
		//???
		public native int sp_prepare(long... args);
		//SP_API void *sp_type(void*, ...);
		//???
		public native long sp_type(long... args);
}
