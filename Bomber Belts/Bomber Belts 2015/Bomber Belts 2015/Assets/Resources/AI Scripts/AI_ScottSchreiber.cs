using UnityEngine;
using System.Collections;

public class AI_ScottSchreiber : MonoBehaviour {

    public CharacterScript mainScript;

    public float[] bombSpeeds;
    public float[] buttonCooldowns;
    public float playerSpeed;
    public int[] beltDirections;
    public float[] buttonLocations;


	//Global variables 
	private float bottomBound = -8f;
	private float topBound = 8f;
	private float bombPlayerBound = 0f;
	private float bombOpponentBound = 18f;
	private int[] intButtonPositions;
	private int targetButtonIndex;

	// Use this for initialization
	void Start () {



        mainScript = GetComponent<CharacterScript>();

        if (mainScript == null)
        {
            print("No CharacterScript found on " + gameObject.name);
            this.enabled = false;
        }

        buttonLocations = mainScript.getButtonLocations();

        playerSpeed = mainScript.getPlayerSpeed();


		intButtonPositions = new int[8];

		for (int i = 0; i < mainScript.getButtonLocations ().Length; i++)
			intButtonPositions [i] = (int)mainScript.getButtonLocations () [i];
	}

	// Update is called once per frame
	void Update () {

        buttonCooldowns = mainScript.getButtonCooldowns();
        beltDirections = mainScript.getBeltDirections();


        
        //Your AI code goes here

		/*
			Notes:
			Button positions are between -8.4 and 8.4, with 2.4 between them
			Character position is between -8.58 and 8.58
			For bomb distance, character side is 0, middle is ~9 and opponent side is ~18
		*/
		RunAI();


	}

	void RunAI(){
		


	}



}
