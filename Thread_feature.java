
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.swabunga.spell.engine.SpellDictionaryHashMap;


public class Thread_feature extends Thread {
	
	//Fields used in the execution of the thread 
	public float multiplier; 
	private String word_typed;
	float value_multiplier; //Insert feature multiplier
	ReentrantLock lock = new ReentrantLock();
	Condition cond = lock.newCondition();
	boolean onGame = true; 
	private SpellDictionaryHashMap dictionaryHashMap;
	WordJudge judger;
	
	public Thread_feature(String word_searched, SpellDictionaryHashMap hashmap_in_question, boolean onGame, float value_mutiplier) {
		this.dictionaryHashMap = hashmap_in_question;
		this.word_typed =word_searched;
		this.multiplier =1;
		this.onGame = onGame;
		this.value_multiplier = value_mutiplier;
		this.judger = new WordJudge(this.word_typed, this.dictionaryHashMap, value_multiplier);
		
	}
	
	public void change_word(String new_word) {
		
		lock.lock();
		try {
			this.word_typed = new_word;
			cond.signalAll();
			
		} finally {
			lock.unlock();
		}
		
	}
	//function used to get the multiplier value from a specific word (that is stored in the field word_typed of this thread)
	public float get_word_multiplier() {
		//Locking the thread to guarantee the Thread Safe condition
		lock.lock();
		try {
			System.out.println(this.word_typed);			
			//We use our Object Judger to give us back the multiplication factor related to this feature 
			this.multiplier = judger.getMultiplier(this.word_typed);
			System.out.println(this.multiplier);
			//We return the multiplier of this feature
			return this.multiplier;			
		} finally {
			cond.signalAll();
			lock.unlock();
		}		
	}
	
	
	public void run () {
		while(onGame) {
			lock.lock(); //to prevent from changing the value of the analysed word
			try {
			String old_word = this.word_typed;
				while(old_word.equals(this.word_typed) && onGame) {
					cond.awaitUninterruptibly(); //we will be using this resource not to make an active wait. Each time a new word is updated we 
					//restart to allow another turn
				}
			} finally {
				lock.unlock();
			}
		}
			
	}
	//function designed to stop the thread when necessary
	public void stopThread () {
		lock.lock();
		try {
			this.onGame = false;
			cond.signalAll();
			
		} finally {
			lock.unlock();
		}
		
	}
	
}

